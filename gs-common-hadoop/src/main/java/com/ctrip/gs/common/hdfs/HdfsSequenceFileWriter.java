package com.ctrip.gs.common.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Writable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctrip.gs.common.util.Config;

/**
 * @author wgji
 * @date：2013年12月25日 下午4:36:06 Writer Data to HDFS
 */
public class HdfsSequenceFileWriter {
    private final Logger logger = LoggerFactory.getLogger(HdfsSequenceFileWriter.class);

    private int batchSize = Config.getInt("hadoop.hdfs.batch.size");

    private int i = 0;
    private Configuration conf = null;
    private Writer writer = null;
    private Writable key = null;
    private Writable val = null;
    private String hdfsPath = null;

    public HdfsSequenceFileWriter(Configuration conf, String hdfsPath, Writable key, Writable val) {
        this.conf = conf;
        this.hdfsPath = hdfsPath;
        this.key = key;
        this.val = val;
    }

    @SuppressWarnings("deprecation")
    private SequenceFile.Writer initWriter() {
        try {
            FileSystem fs = FileSystem.get(conf);
            Path filePath = new Path(hdfsPath);
            writer = SequenceFile.createWriter(fs, conf, filePath, key.getClass(), val.getClass(),
                    CompressionType.BLOCK);
        } catch (IOException e) {
            logger.info("error happened in create sequence file writer:", e);
        }
        return writer;
    }

    @SuppressWarnings("deprecation")
    public void read() {
        SequenceFile.Reader reader = null;
        try {
            FileSystem fs = FileSystem.get(conf);
            Path filePath = new Path(hdfsPath);
            reader = new SequenceFile.Reader(fs, filePath, conf);
            while (reader.next(key, val)) {
                logger.info(key + ":" + val);
            }
        } catch (Exception ex) {
            logger.error("error happened in reading sequence file", ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                logger.error("error happened in closing sequence file reader", e);
            }
        }
    }

    public void write(Writable key, Writable val) {
        if (writer == null) {
            writer = initWriter();
        }
        try {
            writer.append(key, val);
            i = i + 1;
            if (i >= batchSize) {
                writer.hflush();
                writer.sync();
                writer.hsync();
                i = 0;
            }
        } catch (Exception ex) {
            logger.error("Write to HDFS error, key=" + key + "value=" + val, ex);
        }
    }

    public void closeWriter() {
        try {
            writer.hflush();
            writer.sync();
            writer.hsync();
            IOUtils.closeStream(writer);
        } catch (IOException e) {
            logger.info("error happened in close file writer:", e);
        }
    }
}
