package com.ctrip.gs.common.hdfs;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;

/**
 * @author wgji
 * @date：2013年12月25日 下午4:36:06
 */
public class HdfsUtil {
    private static final Logger logger = LoggerFactory.getLogger(HdfsUtil.class);

    public static final String HADOOP_HDSF_PATH = "hadoop.hdfs.path";

    public static void writeInt(String hdfsPath, Configuration conf, int value) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        Path path = new Path(hdfsPath);
        FSDataOutputStream out = fs.create(path);
        try {
            out.writeInt(value);
        } finally {
            Closeables.close(out, false);
        }
    }

    public static int readInt(String hdfsPath, Configuration conf) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        Path path = new Path(hdfsPath);
        FSDataInputStream in = fs.open(path);
        try {
            return in.readInt();
        } finally {
            Closeables.close(in, true);
        }
    }

    public static void mkdirs(Configuration conf, String folder) throws IOException {
        String hdfsPath = conf.get(HADOOP_HDSF_PATH);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        Path path = null;
        if (folder == null || StringUtils.isBlank(folder)) {
            path = new Path(hdfsPath);
        } else {
            path = new Path(folder);
        }
        if (!fs.exists(path)) {
            fs.mkdirs(path);
            logger.info("Create: " + folder);
        }
        fs.close();
    }

    public static void rmr(Configuration conf, String folder) throws IOException {
        String hdfsPath = conf.get(HADOOP_HDSF_PATH);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        Path path = new Path(folder);
        if (fs.exists(path)) {
            fs.delete(path, true);
            logger.info("Delete: " + folder);
        } else {
            logger.info("file doesn't exist");
        }
        fs.close();
    }

    public static void ls(Configuration conf, String hdfsPath) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        Path path = new Path(hdfsPath);
        if (fs.exists(path)) {
            FileStatus[] list = fs.listStatus(path);
            logger.info("ls: " + path.getName());
            logger.info("==========================================================");
            for (FileStatus f : list) {
                logger.info("name:" + f.getPath() + ", folder: " + f.isDirectory() + ",size:" + f.getLen()
                        + ",permission:" + f.getPermission().toString());
                if (f.isDirectory()) {
                    ls(conf, f.getPath().toUri().toString());
                }
            }
            logger.info("==========================================================");
        } else {
            logger.info("file or directory doesn't exist");
        }
        fs.close();
    }

    public static void createFile(Configuration conf, String file, String content) throws IOException {
        String hdfsPath = conf.get(HADOOP_HDSF_PATH);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        byte[] buff = content.getBytes();
        FSDataOutputStream os = null;
        try {
            os = fs.create(new Path(file));
            os.write(buff, 0, buff.length);
            logger.info("Create: " + file);
        } finally {
            if (os != null) {
                os.close();
            }
        }
        fs.close();
    }

    public static void copyFile(Configuration conf, String local, String remote) throws IOException {
        String hdfsPath = conf.get(HADOOP_HDSF_PATH);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        logger.info("copy from: " + local + " to " + remote);
        fs.close();
    }

    public void download(Configuration conf, String remote, String local) throws IOException {
        String hdfsPath = conf.get(HADOOP_HDSF_PATH);
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.copyToLocalFile(path, new Path(local));
        logger.info("download: from" + remote + " to " + local);
        fs.close();
    }

    public static void cat(Configuration conf, String remoteFile) throws IOException {
        String hdfsPath = conf.get(HADOOP_HDSF_PATH);
        Path path = new Path(remoteFile);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        if (fs.exists(path)) {
            FSDataInputStream fsdis = null;
            logger.info("cat: " + remoteFile);
            try {
                fsdis = fs.open(path);
                IOUtils.copyBytes(fsdis, System.out, 4096, false);
            } finally {
                IOUtils.closeStream(fsdis);
                fs.close();
            }
        } else {
            logger.info("file doesn't exist");
            fs.close();
        }
    }
}
