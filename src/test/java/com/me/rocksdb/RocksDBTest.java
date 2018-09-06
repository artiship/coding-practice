package com.me.rocksdb;

import org.junit.Before;
import org.junit.Test;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RocksDBTest {

    private static final Logger LOG = LoggerFactory.getLogger(RocksDBTest.class);

    @Before public void
    setUp() {
    }

    /**
     *
     */
    @Test
    public void init_a_rocks_db_and_do_insert_and_delete() {
        RocksDB.loadLibrary();

        try(final Options options = new Options().setCreateIfMissing(true)) {
            try(final RocksDB db = RocksDB.open(options, "db")) {
                byte[] key1 = "k1".getBytes();
                byte[] key2 = "k2".getBytes();
                // some initialization for key1 and key2

                if(db.get(key2) != null) {
                    LOG.info(new String(db.get(key2)));
                }

                final byte[] value = db.get(key1);

                db.put(key2, value == null ? "value1".getBytes() : value);
                assertThat(db.get(key2), is("value1".getBytes()));
                db.delete(key2);
            }
        } catch (RocksDBException e) {
            LOG.error(e.getStackTrace().toString());
        }
    }

    @Test
    public void
    test_cf_and_merge() {
        RocksDB.loadLibrary();

        try (final ColumnFamilyOptions cfOpts = new ColumnFamilyOptions().optimizeUniversalStyleCompaction().setMergeOperatorName("uint64add").setWriteBufferSize(100);
        ) {
            // list of column family descriptors, first entry must always be default column family
            final List<ColumnFamilyDescriptor> cfDescriptors = Arrays.asList(
                    new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, cfOpts),
                    new ColumnFamilyDescriptor("head".getBytes(), cfOpts),
                    new ColumnFamilyDescriptor("tail".getBytes(), cfOpts)
            );

            // a list which will hold the handles for the column families once the db is opened
            final List<ColumnFamilyHandle> columnFamilyHandleList =
                    new ArrayList<>();

            try (final DBOptions options = new DBOptions().setCreateIfMissing(true).setCreateMissingColumnFamilies(true);
                 final RocksDB db = RocksDB.open(options, "db", cfDescriptors, columnFamilyHandleList)) {
                try {

                    WriteBatch writeBatch = new WriteBatch();
                    WriteOptions writeOptions = new WriteOptions();

                    writeBatch.put("hello".getBytes(), "hello".getBytes());
                    writeBatch.merge(columnFamilyHandleList.get(1), "head".getBytes(), ByteConversionHelper.longToByte(1));
                    writeBatch.merge(columnFamilyHandleList.get(1), "tail".getBytes(), ByteConversionHelper.longToByte(1));

                    db.write(writeOptions, writeBatch);

                    byte[] head = db.get(columnFamilyHandleList.get(1), "tail".getBytes());
                    byte[] tail = db.get(columnFamilyHandleList.get(1), "tail".getBytes());
                    LOG.info("queue head is {}", ByteConversionHelper.byteToLong(head));
                    LOG.info("queue tail is {}", ByteConversionHelper.byteToLong(tail));
                } finally {
                    // NOTE frees the column family handles before freeing the db
                    for (final ColumnFamilyHandle columnFamilyHandle : columnFamilyHandleList) {
                        columnFamilyHandle.close();
                    }
                } // frees the db and the db options
            }// frees the column family options
            catch (RocksDBException e) {
                e.printStackTrace();
            }
        }
    }
}
