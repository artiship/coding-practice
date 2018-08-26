package com.me.rocksdb;

import org.junit.Before;
import org.junit.Test;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            try(final RocksDB db = RocksDB.open(options, "d:/db")) {
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
}
