/*
 * This file is part of memoization.java. It is subject to the license terms in the LICENSE file found in the top-level
 * directory of this distribution and at http://creativecommons.org/publicdomain/zero/1.0/. No part of memoization.java,
 * including this file, may be copied, modified, propagated, or distributed except according to the terms contained
 * in the LICENSE file.
 */
package de.xn__ho_hia.memoization.map;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.ConcurrentMap;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import de.xn__ho_hia.quality.suppression.CompilerWarnings;

final class ConcurrentMapBasedIntSupplierMemoizer<KEY>
        extends ConcurrentMapBasedMemoizer<KEY, Integer>
        implements IntSupplier {

    private final Supplier<KEY> keySupplier;
    private final IntSupplier   supplier;

    @SuppressWarnings(CompilerWarnings.NLS)
    ConcurrentMapBasedIntSupplierMemoizer(
            final ConcurrentMap<KEY, Integer> cache,
            final Supplier<KEY> keySupplier,
            final IntSupplier supplier) {
        super(cache);
        this.keySupplier = requireNonNull(keySupplier,
                "Provide a key function, might just be 'MemoizationDefaults.defaultKeySupplier()'.");
        this.supplier = requireNonNull(supplier,
                "Cannot memoize a NULL Supplier - provide an actual Supplier to fix this.");
    }

    @Override
    public int getAsInt() {
        return computeIfAbsent(keySupplier.get(), input -> Integer.valueOf(supplier.getAsInt())).intValue();
    }

}
