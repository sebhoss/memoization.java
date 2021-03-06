/*
 * This file is part of memoization.java. It is subject to the license terms in the LICENSE file found in the top-level
 * directory of this distribution and at http://creativecommons.org/publicdomain/zero/1.0/. No part of memoization.java,
 * including this file, may be copied, modified, propagated, or distributed except according to the terms contained
 * in the LICENSE file.
 */
package de.xn__ho_hia.memoization.map;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.ConcurrentMap;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

import de.xn__ho_hia.quality.suppression.CompilerWarnings;

final class ConcurrentMapBasedLongUnaryOperatorMemoizer<KEY>
        extends ConcurrentMapBasedMemoizer<KEY, Long>
        implements LongUnaryOperator {

    private final LongFunction<KEY> keyFunction;
    private final LongUnaryOperator operator;

    @SuppressWarnings(CompilerWarnings.NLS)
    ConcurrentMapBasedLongUnaryOperatorMemoizer(
            final ConcurrentMap<KEY, Long> cache,
            final LongFunction<KEY> keyFunction,
            final LongUnaryOperator operator) {
        super(cache);
        this.keyFunction = keyFunction;
        this.operator = requireNonNull(operator,
                "Cannot memoize a NULL LongUnaryOperator - provide an actual LongUnaryOperator to fix this.");
    }

    @Override
    public long applyAsLong(final long operand) {
        final KEY key = keyFunction.apply(operand);
        return computeIfAbsent(key, givenKey -> Long.valueOf(operator.applyAsLong(operand)))
                .intValue();
    }

}
