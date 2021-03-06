/*
 * This file is part of memoization.java. It is subject to the license terms in the LICENSE file found in the top-level
 * directory of this distribution and at http://creativecommons.org/publicdomain/zero/1.0/. No part of memoization.java,
 * including this file, may be copied, modified, propagated, or distributed except according to the terms contained
 * in the LICENSE file.
 */
package de.xn__ho_hia.memoization.map;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.ConcurrentMap;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;

import de.xn__ho_hia.quality.suppression.CompilerWarnings;

final class ConcurrentMapBasedIntToLongFunctionMemoizer<KEY>
        extends ConcurrentMapBasedMemoizer<KEY, Long>
        implements IntToLongFunction {

    private final IntFunction<KEY>  keyFunction;
    private final IntToLongFunction function;

    @SuppressWarnings(CompilerWarnings.NLS)
    ConcurrentMapBasedIntToLongFunctionMemoizer(
            final ConcurrentMap<KEY, Long> cache,
            final IntFunction<KEY> keyFunction,
            final IntToLongFunction function) {
        super(cache);
        this.keyFunction = keyFunction;
        this.function = requireNonNull(function,
                "Cannot memoize a NULL IntToLongFunction - provide an actual IntToLongFunction to fix this.");
    }

    @Override
    public long applyAsLong(final int value) {
        final KEY key = keyFunction.apply(value);
        return computeIfAbsent(key, givenKey -> Long.valueOf(function.applyAsLong(value)))
                .longValue();
    }

}
