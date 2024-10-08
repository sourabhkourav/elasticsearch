/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */

package org.elasticsearch.common.bytes;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefIterator;
import org.elasticsearch.common.util.PageCacheRecycler;

import java.util.stream.IntStream;

/**
 * A {@link BytesReference} of the given length which contains all zeroes.
 */
public class ZeroBytesReference extends AbstractBytesReference {

    public ZeroBytesReference(int length) {
        super(length);
        assert 0 <= length : length;
    }

    @Override
    public int indexOf(byte marker, int from) {
        assert 0 <= from && from <= length : from + " vs " + length;
        if (marker == 0 && from < length) {
            return from;
        } else {
            return -1;
        }
    }

    @Override
    public byte get(int index) {
        assert 0 <= index && index < length : index + " vs " + length;
        return 0;
    }

    @Override
    public BytesReference slice(int from, int length) {
        assert from + length <= this.length : from + " and " + length + " vs " + this.length;
        return new ZeroBytesReference(length);
    }

    @Override
    public long ramBytesUsed() {
        return 0;
    }

    @Override
    public BytesRef toBytesRef() {
        return new BytesRef(new byte[length], 0, length);
    }

    @Override
    public BytesRefIterator iterator() {
        final byte[] buffer = new byte[Math.min(length, PageCacheRecycler.BYTE_PAGE_SIZE)];
        return new BytesRefIterator() {
            int remaining = length;

            @Override
            public BytesRef next() {
                if (IntStream.range(0, buffer.length).map(i -> buffer[i]).anyMatch(b -> b != 0)) {
                    throw new AssertionError("Internal pages from ZeroBytesReference must be zero");
                }
                if (remaining > 0) {
                    final int nextLength = Math.min(remaining, buffer.length);
                    remaining -= nextLength;
                    return new BytesRef(buffer, 0, nextLength);
                } else {
                    return null;
                }
            }
        };
    }
}
