/*
 * This file is part of memoization.java. It is subject to the license terms in the LICENSE file found in the top-level
 * directory of this distribution and at http://creativecommons.org/publicdomain/zero/1.0/. No part of memoization.java,
 * including this file, may be copied, modified, propagated, or distributed except according to the terms contained
 * in the LICENSE file.
 */
package de.xn__ho_hia.memoization.guava;

import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.defaultKeySupplier;
import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.doubleBinaryOperatorHashCodeKeyFunction;
import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.hashCodeKeyFunction;
import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.intBinaryOperatorHashCodeKeyFunction;
import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.longBinaryOperatorHashCodeKeyFunction;
import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.objDoubleConsumerHashCodeKeyFunction;
import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.objIntConsumerHashCodeKeyFunction;
import static de.xn__ho_hia.memoization.shared.MemoizationDefaults.objLongConsumerHashCodeKeyFunction;
import static java.util.function.Function.identity;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.xn__ho_hia.memoization.shared.DoubleBinaryFunction;
import de.xn__ho_hia.memoization.shared.IntBinaryFunction;
import de.xn__ho_hia.memoization.shared.LongBinaryFunction;
import de.xn__ho_hia.memoization.shared.MemoizationDefaults;
import de.xn__ho_hia.memoization.shared.ObjDoubleFunction;
import de.xn__ho_hia.memoization.shared.ObjIntFunction;
import de.xn__ho_hia.memoization.shared.ObjLongFunction;

/**
 * <p>
 * Factory for lightweight wrappers that store the result of a potentially expensive function call. Each method of this
 * class exposes two of the following features:
 * </p>
 * <strong>Default cache</strong>
 * <p>
 * The memoizer uses the default cache of this factory. Current implementation creates a new {@link Cache} per memoizer.
 * </p>
 * <strong>Default cache key</strong>
 * <p>
 * The memoizer uses the default {@link BiFunction} or {@link Supplier} to calculate the cache key for each call. Either
 * uses the natural key (e.g. the input itself) or one of the methods in {@link MemoizationDefaults}.
 * </p>
 * <strong>Custom cache</strong>
 * <p>
 * The memoizer uses a user-provided {@link Cache} as its cache. It is possible to add values to the cache both before
 * and after the memoizer was created.
 * </p>
 * <strong>Custom cache key</strong>
 * <p>
 * The memoizer uses a user-defined {@link BiFunction} or {@link Supplier} to calculate the cache key for each call.
 * Take a look at {@link MemoizationDefaults} for a possible key functions and suppliers.
 * </p>
 *
 * @see BiConsumer
 * @see BiFunction
 * @see BiPredicate
 * @see BooleanSupplier
 * @see Consumer
 * @see DoubleBinaryOperator
 * @see DoubleConsumer
 * @see DoubleFunction
 * @see DoublePredicate
 * @see DoubleSupplier
 * @see DoubleToIntFunction
 * @see DoubleToLongFunction
 * @see DoubleUnaryOperator
 * @see Function
 * @see IntBinaryOperator
 * @see IntConsumer
 * @see IntFunction
 * @see IntPredicate
 * @see IntSupplier
 * @see IntToDoubleFunction
 * @see IntToLongFunction
 * @see IntUnaryOperator
 * @see LongBinaryOperator
 * @see LongConsumer
 * @see LongFunction
 * @see LongPredicate
 * @see LongSupplier
 * @see LongToDoubleFunction
 * @see LongToIntFunction
 * @see LongUnaryOperator
 * @see ObjDoubleConsumer
 * @see ObjIntConsumer
 * @see ObjLongConsumer
 * @see Predicate
 * @see Supplier
 * @see ToDoubleBiFunction
 * @see ToDoubleFunction
 * @see ToIntBiFunction
 * @see ToIntFunction
 * @see ToLongBiFunction
 * @see ToLongFunction
 * @see <a href="https://en.wikipedia.org/wiki/Memoization">Wikipedia: Memoization</a>
 */
public final class GuavaMemoize {

    private GuavaMemoize() {
        // factory class
    }

    /**
     * <p>
     * Memoizes a {@link BiConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param biConsumer
     *            The {@link BiConsumer} to memoize.
     * @return The wrapped {@link BiConsumer}.
     */
    public static final <FIRST, SECOND> BiConsumer<FIRST, SECOND> biConsumer(
            final BiConsumer<FIRST, SECOND> biConsumer) {
        return biConsumer(biConsumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BiConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param biConsumer
     *            The {@link BiConsumer} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @return The wrapped {@link BiConsumer}.
     */
    public static final <FIRST, SECOND, KEY> BiConsumer<FIRST, SECOND> biConsumer(
            final BiConsumer<FIRST, SECOND> biConsumer,
            final BiFunction<FIRST, SECOND, KEY> keyFunction) {
        return biConsumer(biConsumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BiConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param biConsumer
     *            The {@link BiConsumer} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BiConsumer}.
     */
    public static final <FIRST, SECOND, KEY> BiConsumer<FIRST, SECOND> biConsumer(
            final BiConsumer<FIRST, SECOND> biConsumer,
            final BiFunction<FIRST, SECOND, KEY> keyFunction,
            final Cache<KEY, KEY> cache) {
        return new GuavaCacheBasedBiConsumerMemoizer<>(cache, keyFunction, biConsumer);
    }

    /**
     * <p>
     * Memoizes a {@link BiConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param biConsumer
     *            The {@link BiConsumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BiConsumer}.
     */
    public static final <FIRST, SECOND> BiConsumer<FIRST, SECOND> biConsumer(
            final BiConsumer<FIRST, SECOND> biConsumer,
            final Cache<String, String> cache) {
        return biConsumer(biConsumer, hashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link BiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param biFunction
     *            The {@link BiFunction} to memoize.
     * @return The wrapped {@link BiFunction}.
     */
    public static final <FIRST, SECOND, OUTPUT> BiFunction<FIRST, SECOND, OUTPUT> biFunction(
            final BiFunction<FIRST, SECOND, OUTPUT> biFunction) {
        return biFunction(biFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param biFunction
     *            The {@link BiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @return The wrapped {@link BiFunction}.
     */
    public static final <FIRST, SECOND, KEY, OUTPUT> BiFunction<FIRST, SECOND, OUTPUT> biFunction(
            final BiFunction<FIRST, SECOND, OUTPUT> biFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction) {
        return biFunction(biFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param biFunction
     *            The {@link BiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BiFunction}.
     */
    public static final <FIRST, SECOND, KEY, OUTPUT> BiFunction<FIRST, SECOND, OUTPUT> biFunction(
            final BiFunction<FIRST, SECOND, OUTPUT> biFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction,
            final Cache<KEY, OUTPUT> cache) {
        return new GuavaCacheBasedBiFunctionMemoizer<>(cache, keyFunction, biFunction);
    }

    /**
     * <p>
     * Memoizes a {@link BiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param biFunction
     *            The {@link BiFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BiFunction}.
     */
    public static final <FIRST, SECOND, OUTPUT> BiFunction<FIRST, SECOND, OUTPUT> biFunction(
            final BiFunction<FIRST, SECOND, OUTPUT> biFunction,
            final Cache<String, OUTPUT> cache) {
        return biFunction(biFunction, hashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link BiPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param biPredicate
     *            The {@link BiPredicate} to memoize.
     * @return The wrapped {@link BiPredicate}.
     */
    public static final <FIRST, SECOND> BiPredicate<FIRST, SECOND> biPredicate(
            final BiPredicate<FIRST, SECOND> biPredicate) {
        return biPredicate(biPredicate, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BiPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param biPredicate
     *            The {@link BiPredicate} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @return The wrapped {@link BiPredicate}.
     */
    public static final <FIRST, SECOND, KEY> BiPredicate<FIRST, SECOND> biPredicate(
            final BiPredicate<FIRST, SECOND> biPredicate,
            final BiFunction<FIRST, SECOND, KEY> keyFunction) {
        return biPredicate(biPredicate, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BiPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param biPredicate
     *            The {@link BiPredicate} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BiPredicate}.
     */
    public static final <FIRST, SECOND, KEY> BiPredicate<FIRST, SECOND> biPredicate(
            final BiPredicate<FIRST, SECOND> biPredicate,
            final BiFunction<FIRST, SECOND, KEY> keyFunction,
            final Cache<KEY, Boolean> cache) {
        return new GuavaCacheBasedBiPredicateMemoizer<>(cache, keyFunction, biPredicate);
    }

    /**
     * <p>
     * Memoizes a {@link BiPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param biPredicate
     *            The {@link BiPredicate} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BiPredicate}.
     */
    public static final <FIRST, SECOND> BiPredicate<FIRST, SECOND> biPredicate(
            final BiPredicate<FIRST, SECOND> biPredicate,
            final Cache<String, Boolean> cache) {
        return biPredicate(biPredicate, hashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link BooleanSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param booleanSupplier
     *            The {@link BooleanSupplier} to memoize.
     * @return The wrapped {@link BooleanSupplier}.
     */
    public static final BooleanSupplier booleanSupplier(final BooleanSupplier booleanSupplier) {
        return booleanSupplier(booleanSupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BooleanSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param booleanSupplier
     *            The {@link BooleanSupplier} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BooleanSupplier}.
     */
    public static final BooleanSupplier booleanSupplier(
            final BooleanSupplier booleanSupplier,
            final Cache<String, Boolean> cache) {
        return booleanSupplier(booleanSupplier, defaultKeySupplier(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link BooleanSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param booleanSupplier
     *            The {@link BooleanSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @return The wrapped {@link BooleanSupplier}.
     */
    public static final <KEY> BooleanSupplier booleanSupplier(
            final BooleanSupplier booleanSupplier,
            final Supplier<KEY> keySupplier) {
        return booleanSupplier(booleanSupplier, keySupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link BooleanSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param booleanSupplier
     *            The {@link BooleanSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link BooleanSupplier}.
     */
    public static final <KEY> BooleanSupplier booleanSupplier(
            final BooleanSupplier booleanSupplier,
            final Supplier<KEY> keySupplier,
            final Cache<KEY, Boolean> cache) {
        return new GuavaCacheBasedBooleanSupplierMemoizer<>(cache, keySupplier, booleanSupplier);
    }

    /**
     * <p>
     * Memoizes a {@link Consumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param consumer
     *            The {@link Consumer} to memoize.
     * @return The wrapped {@link Consumer}.
     */
    public static final <INPUT> Consumer<INPUT> consumer(
            final Consumer<INPUT> consumer) {
        return consumer(consumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Consumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param consumer
     *            The {@link Consumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Consumer}.
     */
    public static final <INPUT> Consumer<INPUT> consumer(
            final Consumer<INPUT> consumer,
            final Cache<INPUT, INPUT> cache) {
        return consumer(consumer, identity(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link Consumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param consumer
     *            The {@link Consumer} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @return The wrapped {@link Consumer}.
     */
    public static final <INPUT, KEY> Consumer<INPUT> consumer(
            final Consumer<INPUT> consumer,
            final Function<INPUT, KEY> keyFunction) {
        return consumer(consumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Consumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param consumer
     *            The {@link Consumer} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Consumer}.
     */
    public static final <INPUT, KEY> Consumer<INPUT> consumer(
            final Consumer<INPUT> consumer,
            final Function<INPUT, KEY> keyFunction,
            final Cache<KEY, INPUT> cache) {
        return new GuavaCacheBasedConsumerMemoizer<>(cache, keyFunction, consumer);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleBinaryOperator
     *            The {@link DoubleBinaryOperator} to memoize.
     * @return The wrapped {@link DoubleBinaryOperator}.
     */
    public static final DoubleBinaryOperator doubleBinaryOperator(
            final DoubleBinaryOperator doubleBinaryOperator) {
        return doubleBinaryOperator(doubleBinaryOperator, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleBinaryOperator
     *            The {@link DoubleBinaryOperator} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleBinaryOperator}.
     */
    public static final DoubleBinaryOperator doubleBinaryOperator(
            final DoubleBinaryOperator doubleBinaryOperator,
            final Cache<String, Double> cache) {
        return doubleBinaryOperator(doubleBinaryOperator, doubleBinaryOperatorHashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleBinaryOperator
     *            The {@link DoubleBinaryOperator} to memoize.
     * @param keyFunction
     *            The {@link DoubleBinaryFunction} to compute the cache key.
     * @return The wrapped {@link DoubleBinaryOperator}.
     */
    public static final <KEY> DoubleBinaryOperator doubleBinaryOperator(
            final DoubleBinaryOperator doubleBinaryOperator,
            final DoubleBinaryFunction<KEY> keyFunction) {
        return doubleBinaryOperator(doubleBinaryOperator, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleBinaryOperator
     *            The {@link DoubleBinaryOperator} to memoize.
     * @param keyFunction
     *            The {@link DoubleBinaryFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleBinaryOperator}.
     */
    public static final <KEY> DoubleBinaryOperator doubleBinaryOperator(
            final DoubleBinaryOperator doubleBinaryOperator,
            final DoubleBinaryFunction<KEY> keyFunction,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedDoubleBinaryOperatorMemoizer<>(cache, keyFunction, doubleBinaryOperator);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleConsumer
     *            The {@link DoubleConsumer} to memoize.
     * @return The wrapped {@link DoubleConsumer}.
     */
    public static final DoubleConsumer doubleConsumer(
            final DoubleConsumer doubleConsumer) {
        return doubleConsumer(doubleConsumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleConsumer
     *            The {@link DoubleConsumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleConsumer}.
     */
    public static final DoubleConsumer doubleConsumer(
            final DoubleConsumer doubleConsumer,
            final Cache<Double, Double> cache) {
        return doubleConsumer(doubleConsumer, Double::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleConsumer
     *            The {@link DoubleConsumer} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link DoubleConsumer}.
     */
    public static final <KEY> DoubleConsumer doubleConsumer(
            final DoubleConsumer doubleConsumer,
            final DoubleFunction<KEY> keyFunction) {
        return doubleConsumer(doubleConsumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleConsumer
     *            The {@link DoubleConsumer} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleConsumer}.
     */
    public static final <KEY> DoubleConsumer doubleConsumer(
            final DoubleConsumer doubleConsumer,
            final DoubleFunction<KEY> keyFunction,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedDoubleConsumerMemoizer<>(cache, keyFunction, doubleConsumer);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link DoubleFunction} to memoize.
     * @return The wrapped {@link DoubleFunction}.
     */
    public static final <OUTPUT> DoubleFunction<OUTPUT> doubleFunction(
            final DoubleFunction<OUTPUT> function) {
        return doubleFunction(function, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link DoubleFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleFunction}.
     */
    public static final <OUTPUT> DoubleFunction<OUTPUT> doubleFunction(
            final DoubleFunction<OUTPUT> function,
            final Cache<Double, OUTPUT> cache) {
        return doubleFunction(function, Double::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link DoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link DoubleFunction}.
     */
    public static final <KEY, OUTPUT> DoubleFunction<OUTPUT> doubleFunction(
            final DoubleFunction<OUTPUT> function,
            final DoubleFunction<KEY> keyFunction) {
        return doubleFunction(function, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link DoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleFunction}.
     */
    public static final <KEY, OUTPUT> DoubleFunction<OUTPUT> doubleFunction(
            final DoubleFunction<OUTPUT> function,
            final DoubleFunction<KEY> keyFunction,
            final Cache<KEY, OUTPUT> cache) {
        return new GuavaCacheBasedDoubleFunctionMemoizer<>(cache, keyFunction, function);
    }

    /**
     * <p>
     * Memoizes a {@link DoublePredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doublePredicate
     *            The {@link DoublePredicate} to memoize.
     * @return The wrapped {@link DoublePredicate}.
     */
    public static final DoublePredicate doublePredicate(
            final DoublePredicate doublePredicate) {
        return doublePredicate(doublePredicate, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoublePredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doublePredicate
     *            The {@link DoublePredicate} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoublePredicate}.
     */
    public static final DoublePredicate doublePredicate(
            final DoublePredicate doublePredicate,
            final Cache<Double, Boolean> cache) {
        return doublePredicate(doublePredicate, Double::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoublePredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doublePredicate
     *            The {@link DoublePredicate} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link DoublePredicate}.
     */
    public static final <KEY> DoublePredicate doublePredicate(
            final DoublePredicate doublePredicate,
            final DoubleFunction<KEY> keyFunction) {
        return doublePredicate(doublePredicate, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoublePredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doublePredicate
     *            The {@link DoublePredicate} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoublePredicate}.
     */
    public static final <KEY> DoublePredicate doublePredicate(
            final DoublePredicate doublePredicate,
            final DoubleFunction<KEY> keyFunction,
            final Cache<KEY, Boolean> cache) {
        return new GuavaCacheBasedDoublePredicateMemoizer<>(cache, keyFunction, doublePredicate);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleSupplier
     *            The {@link DoubleSupplier} to memoize.
     * @return The wrapped {@link DoubleSupplier}.
     */
    public static final DoubleSupplier doubleSupplier(final DoubleSupplier doubleSupplier) {
        return doubleSupplier(doubleSupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleSupplier
     *            The {@link DoubleSupplier} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleSupplier}.
     */
    public static final DoubleSupplier doubleSupplier(
            final DoubleSupplier doubleSupplier,
            final Cache<String, Double> cache) {
        return doubleSupplier(doubleSupplier, defaultKeySupplier(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleSupplier
     *            The {@link DoubleSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @return The wrapped {@link DoubleSupplier}.
     */
    public static final <KEY> DoubleSupplier doubleSupplier(
            final DoubleSupplier doubleSupplier,
            final Supplier<KEY> keySupplier) {
        return doubleSupplier(doubleSupplier, keySupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleSupplier
     *            The {@link DoubleSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleSupplier}.
     */
    public static final <KEY> DoubleSupplier doubleSupplier(
            final DoubleSupplier doubleSupplier,
            final Supplier<KEY> keySupplier,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedDoubleSupplierMemoizer<>(cache, keySupplier, doubleSupplier);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleToIntFunction
     *            The {@link DoubleToIntFunction} to memoize.
     * @return The wrapped {@link DoubleToIntFunction}.
     */
    public static final DoubleToIntFunction doubleToIntFunction(
            final DoubleToIntFunction doubleToIntFunction) {
        return doubleToIntFunction(doubleToIntFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleToIntFunction
     *            The {@link DoubleToIntFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleToIntFunction}.
     */
    public static final DoubleToIntFunction doubleToIntFunction(
            final DoubleToIntFunction doubleToIntFunction,
            final Cache<Double, Integer> cache) {
        return doubleToIntFunction(doubleToIntFunction, Double::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleToIntFunction
     *            The {@link DoubleToIntFunction} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link DoubleToIntFunction}.
     */
    public static final <KEY> DoubleToIntFunction doubleToIntFunction(
            final DoubleToIntFunction doubleToIntFunction,
            final DoubleFunction<KEY> keyFunction) {
        return doubleToIntFunction(doubleToIntFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleToIntFunction
     *            The {@link DoubleToIntFunction} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleToIntFunction}.
     */
    public static final <KEY> DoubleToIntFunction doubleToIntFunction(
            final DoubleToIntFunction doubleToIntFunction,
            final DoubleFunction<KEY> keyFunction,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedDoubleToIntFunctionMemoizer<>(cache, keyFunction, doubleToIntFunction);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleToLongFunction
     *            The {@link DoubleToLongFunction} to memoize.
     * @return The wrapped {@link DoubleToLongFunction}.
     */
    public static final DoubleToLongFunction doubleToLongFunction(
            final DoubleToLongFunction doubleToLongFunction) {
        return doubleToLongFunction(doubleToLongFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleToLongFunction
     *            The {@link DoubleToLongFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleToLongFunction}.
     */
    public static final DoubleToLongFunction doubleToLongFunction(
            final DoubleToLongFunction doubleToLongFunction,
            final Cache<Double, Long> cache) {
        return doubleToLongFunction(doubleToLongFunction, Double::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleToLongFunction
     *            The {@link DoubleToLongFunction} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link DoubleToLongFunction}.
     */
    public static final <KEY> DoubleToLongFunction doubleToLongFunction(
            final DoubleToLongFunction doubleToLongFunction,
            final DoubleFunction<KEY> keyFunction) {
        return doubleToLongFunction(doubleToLongFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleToLongFunction
     *            The {@link DoubleToLongFunction} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleToLongFunction}.
     */
    public static final <KEY> DoubleToLongFunction doubleToLongFunction(
            final DoubleToLongFunction doubleToLongFunction,
            final DoubleFunction<KEY> keyFunction,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedDoubleToLongFunctionMemoizer<>(cache, keyFunction, doubleToLongFunction);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleUnaryOperator
     *            The {@link DoubleUnaryOperator} to memoize.
     * @return The wrapped {@link DoubleUnaryOperator}.
     */
    public static final DoubleUnaryOperator doubleUnaryOperator(
            final DoubleUnaryOperator doubleUnaryOperator) {
        return doubleUnaryOperator(doubleUnaryOperator, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param doubleUnaryOperator
     *            The {@link DoubleUnaryOperator} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleUnaryOperator}.
     */
    public static final DoubleUnaryOperator doubleUnaryOperator(
            final DoubleUnaryOperator doubleUnaryOperator,
            final Cache<Double, Double> cache) {
        return doubleUnaryOperator(doubleUnaryOperator, Double::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link DoubleUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleUnaryOperator
     *            The {@link DoubleUnaryOperator} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link DoubleUnaryOperator}.
     */
    public static final <KEY> DoubleUnaryOperator doubleUnaryOperator(
            final DoubleUnaryOperator doubleUnaryOperator,
            final DoubleFunction<KEY> keyFunction) {
        return doubleUnaryOperator(doubleUnaryOperator, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link DoubleUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param doubleUnaryOperator
     *            The {@link DoubleUnaryOperator} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link DoubleUnaryOperator}.
     */
    public static final <KEY> DoubleUnaryOperator doubleUnaryOperator(
            final DoubleUnaryOperator doubleUnaryOperator,
            final DoubleFunction<KEY> keyFunction,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedDoubleUnaryOperatorMemoizer<>(cache, keyFunction, doubleUnaryOperator);
    }

    /**
     * <p>
     * Memoizes a {@link Function} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link Function} to memoize.
     * @return The wrapped {@link Function}.
     */
    public static final <INPUT, OUTPUT> Function<INPUT, OUTPUT> function(
            final Function<INPUT, OUTPUT> function) {
        return function(function, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Function} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link Function} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Function}.
     */
    public static final <INPUT, OUTPUT> Function<INPUT, OUTPUT> function(
            final Function<INPUT, OUTPUT> function,
            final Cache<INPUT, OUTPUT> cache) {
        return function(function, identity(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link Function} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link Function} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @return The wrapped {@link Function}.
     */
    public static final <INPUT, KEY, OUTPUT> Function<INPUT, OUTPUT> function(
            final Function<INPUT, OUTPUT> function,
            final Function<INPUT, KEY> keyFunction) {
        return function(function, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Function} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link Function} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Function}.
     */
    public static final <INPUT, KEY, OUTPUT> Function<INPUT, OUTPUT> function(
            final Function<INPUT, OUTPUT> function,
            final Function<INPUT, KEY> keyFunction,
            final Cache<KEY, OUTPUT> cache) {
        return new GuavaCacheBasedFunctionMemoizer<>(cache, keyFunction, function);
    }

    /**
     * <p>
     * Memoizes a {@link IntBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intBinaryOperator
     *            The {@link IntBinaryOperator} to memoize.
     * @return The wrapped {@link IntBinaryOperator}.
     */
    public static final IntBinaryOperator intBinaryOperator(
            final IntBinaryOperator intBinaryOperator) {
        return intBinaryOperator(intBinaryOperator, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intBinaryOperator
     *            The {@link IntBinaryOperator} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntBinaryOperator}.
     */
    public static final IntBinaryOperator intBinaryOperator(
            final IntBinaryOperator intBinaryOperator,
            final Cache<String, Integer> cache) {
        return intBinaryOperator(intBinaryOperator, intBinaryOperatorHashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intBinaryOperator
     *            The {@link IntBinaryOperator} to memoize.
     * @param keyFunction
     *            The {@link IntBinaryFunction} to compute the cache key.
     * @return The wrapped {@link IntBinaryOperator}.
     */
    public static final <KEY> IntBinaryOperator intBinaryOperator(
            final IntBinaryOperator intBinaryOperator,
            final IntBinaryFunction<KEY> keyFunction) {
        return intBinaryOperator(intBinaryOperator, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intBinaryOperator
     *            The {@link IntBinaryOperator} to memoize.
     * @param keyFunction
     *            The {@link IntBinaryFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntBinaryOperator}.
     */
    public static final <KEY> IntBinaryOperator intBinaryOperator(
            final IntBinaryOperator intBinaryOperator,
            final IntBinaryFunction<KEY> keyFunction,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedIntBinaryOperatorMemoizer<>(cache, keyFunction, intBinaryOperator);
    }

    /**
     * <p>
     * Memoizes a {@link IntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intConsumer
     *            The {@link IntConsumer} to memoize.
     * @return The wrapped {@link IntConsumer}.
     */
    public static final IntConsumer intConsumer(
            final IntConsumer intConsumer) {
        return intConsumer(intConsumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intConsumer
     *            The {@link IntConsumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntConsumer}.
     */
    public static final IntConsumer intConsumer(
            final IntConsumer intConsumer,
            final Cache<Integer, Integer> cache) {
        return intConsumer(intConsumer, Integer::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intConsumer
     *            The {@link IntConsumer} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link IntConsumer}.
     */
    public static final <KEY> IntConsumer intConsumer(
            final IntConsumer intConsumer,
            final IntFunction<KEY> keyFunction) {
        return intConsumer(intConsumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intConsumer
     *            The {@link IntConsumer} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntConsumer}.
     */
    public static final <KEY> IntConsumer intConsumer(
            final IntConsumer intConsumer,
            final IntFunction<KEY> keyFunction,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedIntConsumerMemoizer<>(cache, keyFunction, intConsumer);
    }

    /**
     * <p>
     * Memoizes a {@link IntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link IntFunction} to memoize.
     * @return The wrapped {@link IntFunction}.
     */
    public static final <OUTPUT> IntFunction<OUTPUT> intFunction(
            final IntFunction<OUTPUT> function) {
        return intFunction(function, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link IntFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntFunction}.
     */
    public static final <OUTPUT> IntFunction<OUTPUT> intFunction(
            final IntFunction<OUTPUT> function,
            final Cache<Integer, OUTPUT> cache) {
        return intFunction(function, Integer::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link IntFunction} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @return The wrapped {@link IntFunction}.
     */
    public static final <KEY, OUTPUT> IntFunction<OUTPUT> intFunction(
            final IntFunction<OUTPUT> function,
            final IntFunction<KEY> keyFunction) {
        return intFunction(function, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link IntFunction} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntFunction}.
     */
    public static final <KEY, OUTPUT> IntFunction<OUTPUT> intFunction(
            final IntFunction<OUTPUT> function,
            final IntFunction<KEY> keyFunction,
            final Cache<KEY, OUTPUT> cache) {
        return new GuavaCacheBasedIntFunctionMemoizer<>(cache, keyFunction, function);
    }

    /**
     * <p>
     * Memoizes a {@link IntPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intPredicate
     *            The {@link IntPredicate} to memoize.
     * @return The wrapped {@link IntPredicate}.
     */
    public static final IntPredicate intPredicate(
            final IntPredicate intPredicate) {
        return intPredicate(intPredicate, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intPredicate
     *            The {@link IntPredicate} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntPredicate}.
     */
    public static final IntPredicate intPredicate(
            final IntPredicate intPredicate,
            final Cache<Integer, Boolean> cache) {
        return intPredicate(intPredicate, Integer::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intPredicate
     *            The {@link IntPredicate} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @return The wrapped {@link IntPredicate}.
     */
    public static final <KEY> IntPredicate intPredicate(
            final IntPredicate intPredicate,
            final IntFunction<KEY> keyFunction) {
        return intPredicate(intPredicate, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intPredicate
     *            The {@link IntPredicate} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntPredicate}.
     */
    public static final <KEY> IntPredicate intPredicate(
            final IntPredicate intPredicate,
            final IntFunction<KEY> keyFunction,
            final Cache<KEY, Boolean> cache) {
        return new GuavaCacheBasedIntPredicateMemoizer<>(cache, keyFunction, intPredicate);
    }

    /**
     * <p>
     * Memoizes a {@link IntSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intSupplier
     *            The {@link IntSupplier} to memoize.
     * @return The wrapped {@link IntSupplier}.
     */
    public static final IntSupplier intSupplier(final IntSupplier intSupplier) {
        return intSupplier(intSupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intSupplier
     *            The {@link IntSupplier} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntSupplier}.
     */
    public static final IntSupplier intSupplier(
            final IntSupplier intSupplier,
            final Cache<String, Integer> cache) {
        return intSupplier(intSupplier, defaultKeySupplier(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intSupplier
     *            The {@link IntSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @return The wrapped {@link IntSupplier}.
     */
    public static final <KEY> IntSupplier intSupplier(
            final IntSupplier intSupplier,
            final Supplier<KEY> keySupplier) {
        return intSupplier(intSupplier, keySupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intSupplier
     *            The {@link IntSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntSupplier}.
     */
    public static final <KEY> IntSupplier intSupplier(
            final IntSupplier intSupplier,
            final Supplier<KEY> keySupplier,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedIntSupplierMemoizer<>(cache, keySupplier, intSupplier);
    }

    /**
     * <p>
     * Memoizes a {@link IntToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intToDoubleFunction
     *            The {@link IntToDoubleFunction} to memoize.
     * @return The wrapped {@link IntToDoubleFunction}.
     */
    public static final IntToDoubleFunction intToDoubleFunction(
            final IntToDoubleFunction intToDoubleFunction) {
        return intToDoubleFunction(intToDoubleFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intToDoubleFunction
     *            The {@link IntToDoubleFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntToDoubleFunction}.
     */
    public static final IntToDoubleFunction intToDoubleFunction(
            final IntToDoubleFunction intToDoubleFunction,
            final Cache<Integer, Double> cache) {
        return intToDoubleFunction(intToDoubleFunction, Integer::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intToDoubleFunction
     *            The {@link IntToDoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @return The wrapped {@link IntToDoubleFunction}.
     */
    public static final <KEY> IntToDoubleFunction intToDoubleFunction(
            final IntToDoubleFunction intToDoubleFunction,
            final IntFunction<KEY> keyFunction) {
        return intToDoubleFunction(intToDoubleFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intToDoubleFunction
     *            The {@link IntToDoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntToDoubleFunction}.
     */
    public static final <KEY> IntToDoubleFunction intToDoubleFunction(
            final IntToDoubleFunction intToDoubleFunction,
            final IntFunction<KEY> keyFunction,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedIntToDoubleFunctionMemoizer<>(cache, keyFunction, intToDoubleFunction);
    }

    /**
     * <p>
     * Memoizes a {@link IntToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intToLongFunction
     *            The {@link IntToLongFunction} to memoize.
     * @return The wrapped {@link IntToLongFunction}.
     */
    public static final IntToLongFunction intToLongFunction(
            final IntToLongFunction intToLongFunction) {
        return intToLongFunction(intToLongFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intToLongFunction
     *            The {@link IntToLongFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntToLongFunction}.
     */
    public static final IntToLongFunction intToLongFunction(
            final IntToLongFunction intToLongFunction,
            final Cache<Integer, Long> cache) {
        return intToLongFunction(intToLongFunction, Integer::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intToLongFunction
     *            The {@link IntToLongFunction} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @return The wrapped {@link IntToLongFunction}.
     */
    public static final <KEY> IntToLongFunction intToLongFunction(
            final IntToLongFunction intToLongFunction,
            final IntFunction<KEY> keyFunction) {
        return intToLongFunction(intToLongFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intToLongFunction
     *            The {@link IntToLongFunction} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntToLongFunction}.
     */
    public static final <KEY> IntToLongFunction intToLongFunction(
            final IntToLongFunction intToLongFunction,
            final IntFunction<KEY> keyFunction,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedIntToLongFunctionMemoizer<>(cache, keyFunction, intToLongFunction);
    }

    /**
     * <p>
     * Memoizes a {@link IntUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intUnaryOperator
     *            The {@link IntUnaryOperator} to memoize.
     * @return The wrapped {@link IntUnaryOperator}.
     */
    public static final IntUnaryOperator intUnaryOperator(
            final IntUnaryOperator intUnaryOperator) {
        return intUnaryOperator(intUnaryOperator, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param intUnaryOperator
     *            The {@link IntToLongFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntUnaryOperator}.
     */
    public static final IntUnaryOperator intUnaryOperator(
            final IntUnaryOperator intUnaryOperator,
            final Cache<Integer, Integer> cache) {
        return intUnaryOperator(intUnaryOperator, Integer::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link IntUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intUnaryOperator
     *            The {@link IntUnaryOperator} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @return The wrapped {@link IntUnaryOperator}.
     */
    public static final <KEY> IntUnaryOperator intUnaryOperator(
            final IntUnaryOperator intUnaryOperator,
            final IntFunction<KEY> keyFunction) {
        return intUnaryOperator(intUnaryOperator, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link IntUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param intUnaryOperator
     *            The {@link IntUnaryOperator} to memoize.
     * @param keyFunction
     *            The {@link IntFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link IntUnaryOperator}.
     */
    public static final <KEY> IntUnaryOperator intUnaryOperator(
            final IntUnaryOperator intUnaryOperator,
            final IntFunction<KEY> keyFunction,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedIntUnaryOperatorMemoizer<>(cache, keyFunction, intUnaryOperator);
    }

    /**
     * <p>
     * Memoizes a {@link LongBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longBinaryOperator
     *            The {@link LongBinaryOperator} to memoize.
     * @return The wrapped {@link LongBinaryOperator}.
     */
    public static final LongBinaryOperator longBinaryOperator(
            final LongBinaryOperator longBinaryOperator) {
        return longBinaryOperator(longBinaryOperator, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longBinaryOperator
     *            The {@link LongBinaryOperator} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongBinaryOperator}.
     */
    public static final LongBinaryOperator longBinaryOperator(
            final LongBinaryOperator longBinaryOperator,
            final Cache<String, Long> cache) {
        return longBinaryOperator(longBinaryOperator, longBinaryOperatorHashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longBinaryOperator
     *            The {@link LongBinaryOperator} to memoize.
     * @param keyFunction
     *            The {@link LongBinaryFunction} to compute the cache key.
     * @return The wrapped {@link LongBinaryOperator}.
     */
    public static final <KEY> LongBinaryOperator longBinaryOperator(
            final LongBinaryOperator longBinaryOperator,
            final LongBinaryFunction<KEY> keyFunction) {
        return longBinaryOperator(longBinaryOperator, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongBinaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longBinaryOperator
     *            The {@link LongBinaryOperator} to memoize.
     * @param keyFunction
     *            The {@link LongBinaryFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongBinaryOperator}.
     */
    public static final <KEY> LongBinaryOperator longBinaryOperator(
            final LongBinaryOperator longBinaryOperator,
            final LongBinaryFunction<KEY> keyFunction,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedLongBinaryOperatorMemoizer<>(cache, keyFunction, longBinaryOperator);
    }

    /**
     * <p>
     * Memoizes a {@link LongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longConsumer
     *            The {@link LongConsumer} to memoize.
     * @return The wrapped {@link LongConsumer}.
     */
    public static final LongConsumer longConsumer(
            final LongConsumer longConsumer) {
        return longConsumer(longConsumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longConsumer
     *            The {@link LongConsumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongConsumer}.
     */
    public static final LongConsumer longConsumer(
            final LongConsumer longConsumer,
            final Cache<Long, Long> cache) {
        return longConsumer(longConsumer, Long::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longConsumer
     *            The {@link LongConsumer} to memoize.
     * @param keyFunction
     *            The {@link DoubleFunction} to compute the cache key.
     * @return The wrapped {@link LongConsumer}.
     */
    public static final <KEY> LongConsumer longConsumer(
            final LongConsumer longConsumer,
            final LongFunction<KEY> keyFunction) {
        return longConsumer(longConsumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longConsumer
     *            The {@link LongConsumer} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongConsumer}.
     */
    public static final <KEY> LongConsumer longConsumer(
            final LongConsumer longConsumer,
            final LongFunction<KEY> keyFunction,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedLongConsumerMemoizer<>(cache, keyFunction, longConsumer);
    }

    /**
     * <p>
     * Memoizes a {@link LongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link LongFunction} to memoize.
     * @return The wrapped {@link LongFunction}.
     */
    public static final <OUTPUT> LongFunction<OUTPUT> longFunction(
            final LongFunction<OUTPUT> function) {
        return longFunction(function, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link LongFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongFunction}.
     */
    public static final <OUTPUT> LongFunction<OUTPUT> longFunction(
            final LongFunction<OUTPUT> function,
            final Cache<Long, OUTPUT> cache) {
        return longFunction(function, Long::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link LongFunction} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @return The wrapped {@link LongFunction}.
     */
    public static final <KEY, OUTPUT> LongFunction<OUTPUT> longFunction(
            final LongFunction<OUTPUT> function,
            final LongFunction<KEY> keyFunction) {
        return longFunction(function, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param function
     *            The {@link LongFunction} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongFunction}.
     */
    public static final <KEY, OUTPUT> LongFunction<OUTPUT> longFunction(
            final LongFunction<OUTPUT> function,
            final LongFunction<KEY> keyFunction,
            final Cache<KEY, OUTPUT> cache) {
        return new GuavaCacheBasedLongFunctionMemoizer<>(cache, keyFunction, function);
    }

    /**
     * <p>
     * Memoizes a {@link LongPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longPredicate
     *            The {@link LongPredicate} to memoize.
     * @return The wrapped {@link LongPredicate}.
     */
    public static final LongPredicate longPredicate(
            final LongPredicate longPredicate) {
        return longPredicate(longPredicate, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longPredicate
     *            The {@link LongPredicate} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongPredicate}.
     */
    public static final LongPredicate longPredicate(
            final LongPredicate longPredicate,
            final Cache<Long, Boolean> cache) {
        return longPredicate(longPredicate, Long::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longPredicate
     *            The {@link LongPredicate} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @return The wrapped {@link LongPredicate}.
     */
    public static final <KEY> LongPredicate longPredicate(
            final LongPredicate longPredicate,
            final LongFunction<KEY> keyFunction) {
        return longPredicate(longPredicate, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongPredicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longPredicate
     *            The {@link LongPredicate} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongPredicate}.
     */
    public static final <KEY> LongPredicate longPredicate(
            final LongPredicate longPredicate,
            final LongFunction<KEY> keyFunction,
            final Cache<KEY, Boolean> cache) {
        return new GuavaCacheBasedLongPredicateMemoizer<>(cache, keyFunction, longPredicate);
    }

    /**
     * <p>
     * Memoizes a {@link LongSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longSupplier
     *            The {@link LongSupplier} to memoize.
     * @return The wrapped {@link LongSupplier}.
     */
    public static final LongSupplier longSupplier(final LongSupplier longSupplier) {
        return longSupplier(longSupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longSupplier
     *            The {@link LongSupplier} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongSupplier}.
     */
    public static final LongSupplier longSupplier(
            final LongSupplier longSupplier,
            final Cache<String, Long> cache) {
        return longSupplier(longSupplier, defaultKeySupplier(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longSupplier
     *            The {@link LongSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @return The wrapped {@link LongSupplier}.
     */
    public static final <KEY> LongSupplier longSupplier(
            final LongSupplier longSupplier,
            final Supplier<KEY> keySupplier) {
        return longSupplier(longSupplier, keySupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongSupplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longSupplier
     *            The {@link LongSupplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongSupplier}.
     */
    public static final <KEY> LongSupplier longSupplier(
            final LongSupplier longSupplier,
            final Supplier<KEY> keySupplier,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedLongSupplierMemoizer<>(cache, keySupplier, longSupplier);
    }

    /**
     * <p>
     * Memoizes a {@link LongToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longToDoubleFunction
     *            The {@link LongToDoubleFunction} to memoize.
     * @return The wrapped {@link LongToDoubleFunction}.
     */
    public static final LongToDoubleFunction longToDoubleFunction(
            final LongToDoubleFunction longToDoubleFunction) {
        return longToDoubleFunction(longToDoubleFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longToDoubleFunction
     *            The {@link LongToDoubleFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongToDoubleFunction}.
     */
    public static final LongToDoubleFunction longToDoubleFunction(
            final LongToDoubleFunction longToDoubleFunction,
            final Cache<Long, Double> cache) {
        return longToDoubleFunction(longToDoubleFunction, Long::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longToDoubleFunction
     *            The {@link LongToDoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @return The wrapped {@link LongToDoubleFunction}.
     */
    public static final <KEY> LongToDoubleFunction longToDoubleFunction(
            final LongToDoubleFunction longToDoubleFunction,
            final LongFunction<KEY> keyFunction) {
        return longToDoubleFunction(longToDoubleFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longToDoubleFunction
     *            The {@link LongToDoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongToDoubleFunction}.
     */
    public static final <KEY> LongToDoubleFunction longToDoubleFunction(
            final LongToDoubleFunction longToDoubleFunction,
            final LongFunction<KEY> keyFunction,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedLongToDoubleFunctionMemoizer<>(cache, keyFunction, longToDoubleFunction);
    }

    /**
     * <p>
     * Memoizes a {@link LongToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longToIntFunction
     *            The {@link LongToIntFunction} to memoize.
     * @return The wrapped {@link LongToIntFunction}.
     */
    public static final LongToIntFunction longToIntFunction(
            final LongToIntFunction longToIntFunction) {
        return longToIntFunction(longToIntFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longToIntFunction
     *            The {@link LongToIntFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongToIntFunction}.
     */
    public static final LongToIntFunction longToIntFunction(
            final LongToIntFunction longToIntFunction,
            final Cache<Long, Integer> cache) {
        return longToIntFunction(longToIntFunction, Long::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longToIntFunction
     *            The {@link LongToIntFunction} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @return The wrapped {@link LongToIntFunction}.
     */
    public static final <KEY> LongToIntFunction longToIntFunction(
            final LongToIntFunction longToIntFunction,
            final LongFunction<KEY> keyFunction) {
        return longToIntFunction(longToIntFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longToIntFunction
     *            The {@link LongToIntFunction} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongToIntFunction}.
     */
    public static final <KEY> LongToIntFunction longToIntFunction(
            final LongToIntFunction longToIntFunction,
            final LongFunction<KEY> keyFunction,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedLongToIntFunctionMemoizer<>(cache, keyFunction, longToIntFunction);
    }

    /**
     * <p>
     * Memoizes a {@link LongUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longUnaryOperator
     *            The {@link LongUnaryOperator} to memoize.
     * @return The wrapped {@link LongUnaryOperator}.
     */
    public static final LongUnaryOperator longUnaryOperator(
            final LongUnaryOperator longUnaryOperator) {
        return longUnaryOperator(longUnaryOperator, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param longUnaryOperator
     *            The {@link LongUnaryOperator} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongUnaryOperator}.
     */
    public static final LongUnaryOperator longUnaryOperator(
            final LongUnaryOperator longUnaryOperator,
            final Cache<Long, Long> cache) {
        return longUnaryOperator(longUnaryOperator, Long::valueOf, cache);
    }

    /**
     * <p>
     * Memoizes a {@link LongUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longUnaryOperator
     *            The {@link LongUnaryOperator} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @return The wrapped {@link LongUnaryOperator}.
     */
    public static final <KEY> LongUnaryOperator longUnaryOperator(
            final LongUnaryOperator longUnaryOperator,
            final LongFunction<KEY> keyFunction) {
        return longUnaryOperator(longUnaryOperator, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link LongUnaryOperator} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param longUnaryOperator
     *            The {@link LongUnaryOperator} to memoize.
     * @param keyFunction
     *            The {@link LongFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link LongUnaryOperator}.
     */
    public static final <KEY> LongUnaryOperator longUnaryOperator(
            final LongUnaryOperator longUnaryOperator,
            final LongFunction<KEY> keyFunction,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedLongUnaryOperatorMemoizer<>(cache, keyFunction, longUnaryOperator);
    }

    /**
     * <p>
     * Memoizes a {@link ObjDoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param objDoubleConsumer
     *            The {@link ObjDoubleConsumer} to memoize.
     * @return The wrapped {@link ObjDoubleConsumer}.
     */
    public static final <INPUT> ObjDoubleConsumer<INPUT> objDoubleConsumer(
            final ObjDoubleConsumer<INPUT> objDoubleConsumer) {
        return objDoubleConsumer(objDoubleConsumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ObjDoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param objDoubleConsumer
     *            The {@link ObjDoubleConsumer} to memoize.
     * @param keyFunction
     *            The {@link ObjDoubleFunction} to compute the cache key.
     * @return The wrapped {@link ObjDoubleConsumer}.
     */
    public static final <INPUT, KEY> ObjDoubleConsumer<INPUT> objDoubleConsumer(
            final ObjDoubleConsumer<INPUT> objDoubleConsumer,
            final ObjDoubleFunction<INPUT, KEY> keyFunction) {
        return objDoubleConsumer(objDoubleConsumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ObjDoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param objDoubleConsumer
     *            The {@link ObjDoubleConsumer} to memoize.
     * @param keyFunction
     *            The {@link ObjDoubleFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ObjDoubleConsumer}.
     */
    public static final <INPUT, KEY> ObjDoubleConsumer<INPUT> objDoubleConsumer(
            final ObjDoubleConsumer<INPUT> objDoubleConsumer,
            final ObjDoubleFunction<INPUT, KEY> keyFunction,
            final Cache<KEY, KEY> cache) {
        return new GuavaCacheBasedObjDoubleConsumerMemoizer<>(cache, keyFunction, objDoubleConsumer);
    }

    /**
     * <p>
     * Memoizes a {@link ObjDoubleConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param objDoubleConsumer
     *            The {@link ObjDoubleConsumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ObjDoubleConsumer}.
     */
    public static final <INPUT> ObjDoubleConsumer<INPUT> objDoubleConsumer(
            final ObjDoubleConsumer<INPUT> objDoubleConsumer,
            final Cache<String, String> cache) {
        return objDoubleConsumer(objDoubleConsumer, objDoubleConsumerHashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ObjIntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param objIntConsumer
     *            The {@link ObjIntConsumer} to memoize.
     * @return The wrapped {@link ObjIntConsumer}.
     */
    public static final <INPUT> ObjIntConsumer<INPUT> objIntConsumer(
            final ObjIntConsumer<INPUT> objIntConsumer) {
        return objIntConsumer(objIntConsumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ObjIntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param objIntConsumer
     *            The {@link ObjIntConsumer} to memoize.
     * @param keyFunction
     *            The {@link ObjIntFunction} to compute the cache key.
     * @return The wrapped {@link ObjIntConsumer}.
     */
    public static final <INPUT, KEY> ObjIntConsumer<INPUT> objIntConsumer(
            final ObjIntConsumer<INPUT> objIntConsumer,
            final ObjIntFunction<INPUT, KEY> keyFunction) {
        return objIntConsumer(objIntConsumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ObjIntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param objIntConsumer
     *            The {@link ObjIntConsumer} to memoize.
     * @param keyFunction
     *            The {@link ObjIntFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ObjIntConsumer}.
     */
    public static final <INPUT, KEY> ObjIntConsumer<INPUT> objIntConsumer(
            final ObjIntConsumer<INPUT> objIntConsumer,
            final ObjIntFunction<INPUT, KEY> keyFunction,
            final Cache<KEY, KEY> cache) {
        return new GuavaCacheBasedObjIntConsumerMemoizer<>(cache, keyFunction, objIntConsumer);
    }

    /**
     * <p>
     * Memoizes a {@link ObjIntConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param objIntConsumer
     *            The {@link ObjIntConsumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ObjIntConsumer}.
     */
    public static final <INPUT> ObjIntConsumer<INPUT> objIntConsumer(
            final ObjIntConsumer<INPUT> objIntConsumer,
            final Cache<String, String> cache) {
        return objIntConsumer(objIntConsumer, objIntConsumerHashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ObjLongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param objLongConsumer
     *            The {@link ObjLongConsumer} to memoize.
     * @return The wrapped {@link ObjLongConsumer}.
     */
    public static final <INPUT> ObjLongConsumer<INPUT> objLongConsumer(
            final ObjLongConsumer<INPUT> objLongConsumer) {
        return objLongConsumer(objLongConsumer, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ObjLongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param objLongConsumer
     *            The {@link ObjLongConsumer} to memoize.
     * @param keyFunction
     *            The {@link ObjLongFunction} to compute the cache key.
     * @return The wrapped {@link ObjLongConsumer}.
     */
    public static final <INPUT, KEY> ObjLongConsumer<INPUT> objLongConsumer(
            final ObjLongConsumer<INPUT> objLongConsumer,
            final ObjLongFunction<INPUT, KEY> keyFunction) {
        return objLongConsumer(objLongConsumer, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ObjLongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param objLongConsumer
     *            The {@link ObjLongConsumer} to memoize.
     * @param keyFunction
     *            The {@link ObjLongFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ObjLongConsumer}.
     */
    public static final <INPUT, KEY> ObjLongConsumer<INPUT> objLongConsumer(
            final ObjLongConsumer<INPUT> objLongConsumer,
            final ObjLongFunction<INPUT, KEY> keyFunction,
            final Cache<KEY, KEY> cache) {
        return new GuavaCacheBasedObjLongConsumerMemoizer<>(cache, keyFunction, objLongConsumer);
    }

    /**
     * <p>
     * Memoizes a {@link ObjLongConsumer} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param objLongConsumer
     *            The {@link ObjLongConsumer} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ObjLongConsumer}.
     */
    public static final <INPUT> ObjLongConsumer<INPUT> objLongConsumer(
            final ObjLongConsumer<INPUT> objLongConsumer,
            final Cache<String, String> cache) {
        return objLongConsumer(objLongConsumer, objLongConsumerHashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link Predicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param predicate
     *            The {@link Predicate} to memoize.
     * @return The wrapped {@link Predicate}.
     */
    public static final <INPUT> Predicate<INPUT> predicate(
            final Predicate<INPUT> predicate) {
        return predicate(predicate, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Predicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param predicate
     *            The {@link Predicate} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Predicate}.
     */
    public static final <INPUT> Predicate<INPUT> predicate(
            final Predicate<INPUT> predicate,
            final Cache<INPUT, Boolean> cache) {
        return predicate(predicate, identity(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link Predicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param predicate
     *            The {@link Predicate} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @return The wrapped {@link Predicate}.
     */
    public static final <INPUT, KEY> Predicate<INPUT> predicate(
            final Predicate<INPUT> predicate,
            final Function<INPUT, KEY> keyFunction) {
        return predicate(predicate, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Predicate} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param predicate
     *            The {@link Predicate} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Predicate}.
     */
    public static final <INPUT, KEY> Predicate<INPUT> predicate(
            final Predicate<INPUT> predicate,
            final Function<INPUT, KEY> keyFunction,
            final Cache<KEY, Boolean> cache) {
        return new GuavaCacheBasedPredicateMemoizer<>(cache, keyFunction, predicate);
    }

    /**
     * <p>
     * Memoizes a {@link Supplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param supplier
     *            The {@link Supplier} to memoize.
     * @return The wrapped {@link Supplier}.
     */
    public static final <OUTPUT> Supplier<OUTPUT> supplier(final Supplier<OUTPUT> supplier) {
        return supplier(supplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Supplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param supplier
     *            The {@link Supplier} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Supplier}.
     */
    public static final <OUTPUT> Supplier<OUTPUT> supplier(
            final Supplier<OUTPUT> supplier,
            final Cache<String, OUTPUT> cache) {
        return supplier(supplier, defaultKeySupplier(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link Supplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param supplier
     *            The {@link Supplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @return The wrapped {@link Supplier}.
     */
    public static final <KEY, OUTPUT> Supplier<OUTPUT> supplier(
            final Supplier<OUTPUT> supplier,
            final Supplier<KEY> keySupplier) {
        return supplier(supplier, keySupplier, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link Supplier} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <KEY>
     *            The type of the cache key.
     * @param <OUTPUT>
     *            The type of the output/cache value.
     * @param supplier
     *            The {@link Supplier} to memoize.
     * @param keySupplier
     *            The {@link Supplier} for the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link Supplier}.
     */
    public static final <KEY, OUTPUT> Supplier<OUTPUT> supplier(
            final Supplier<OUTPUT> supplier,
            final Supplier<KEY> keySupplier,
            final Cache<KEY, OUTPUT> cache) {
        return new GuavaCacheBasedSupplierMemoizer<>(cache, keySupplier, supplier);
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param toDoubleBiFunction
     *            The {@link ToDoubleBiFunction} to memoize.
     * @return The wrapped {@link ToDoubleBiFunction}.
     */
    public static final <FIRST, SECOND> ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction(
            final ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction) {
        return toDoubleBiFunction(toDoubleBiFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param toDoubleBiFunction
     *            The {@link ToDoubleBiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @return The wrapped {@link ToDoubleBiFunction}.
     */
    public static final <FIRST, SECOND, KEY> ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction(
            final ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction) {
        return toDoubleBiFunction(toDoubleBiFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param toDoubleBiFunction
     *            The {@link ToDoubleBiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToDoubleBiFunction}.
     */
    public static final <FIRST, SECOND, KEY> ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction(
            final ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedToDoubleBiFunctionMemoizer<>(cache, keyFunction, toDoubleBiFunction);
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param toDoubleBiFunction
     *            The {@link ToDoubleBiFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToDoubleBiFunction}.
     */
    public static final <FIRST, SECOND> ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction(
            final ToDoubleBiFunction<FIRST, SECOND> toDoubleBiFunction,
            final Cache<String, Double> cache) {
        return toDoubleBiFunction(toDoubleBiFunction, hashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param toDoubleFunction
     *            The {@link ToDoubleFunction} to memoize.
     * @return The wrapped {@link ToDoubleFunction}.
     */
    public static final <INPUT> ToDoubleFunction<INPUT> toDoubleFunction(
            final ToDoubleFunction<INPUT> toDoubleFunction) {
        return toDoubleFunction(toDoubleFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param toDoubleFunction
     *            The {@link ToDoubleFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToDoubleFunction}.
     */
    public static final <INPUT> ToDoubleFunction<INPUT> toDoubleFunction(
            final ToDoubleFunction<INPUT> toDoubleFunction,
            final Cache<INPUT, Double> cache) {
        return toDoubleFunction(toDoubleFunction, identity(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param toDoubleFunction
     *            The {@link ToDoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @return The wrapped {@link ToDoubleFunction}.
     */
    public static final <INPUT, KEY> ToDoubleFunction<INPUT> toDoubleFunction(
            final ToDoubleFunction<INPUT> toDoubleFunction,
            final Function<INPUT, KEY> keyFunction) {
        return toDoubleFunction(toDoubleFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToDoubleFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param toDoubleFunction
     *            The {@link ToDoubleFunction} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToDoubleFunction}.
     */
    public static final <INPUT, KEY> ToDoubleFunction<INPUT> toDoubleFunction(
            final ToDoubleFunction<INPUT> toDoubleFunction,
            final Function<INPUT, KEY> keyFunction,
            final Cache<KEY, Double> cache) {
        return new GuavaCacheBasedToDoubleFunctionMemoizer<>(cache, keyFunction, toDoubleFunction);
    }

    /**
     * <p>
     * Memoizes a {@link ToIntBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param toIntBiFunction
     *            The {@link ToIntBiFunction} to memoize.
     * @return The wrapped {@link ToIntBiFunction}.
     */
    public static final <FIRST, SECOND> ToIntBiFunction<FIRST, SECOND> toIntBiFunction(
            final ToIntBiFunction<FIRST, SECOND> toIntBiFunction) {
        return toIntBiFunction(toIntBiFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToIntBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param toIntBiFunction
     *            The {@link ToIntBiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @return The wrapped {@link ToIntBiFunction}.
     */
    public static final <FIRST, SECOND, KEY> ToIntBiFunction<FIRST, SECOND> toIntBiFunction(
            final ToIntBiFunction<FIRST, SECOND> toIntBiFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction) {
        return toIntBiFunction(toIntBiFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToIntBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param toIntBiFunction
     *            The {@link ToIntBiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToIntBiFunction}.
     */
    public static final <FIRST, SECOND, KEY> ToIntBiFunction<FIRST, SECOND> toIntBiFunction(
            final ToIntBiFunction<FIRST, SECOND> toIntBiFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedToIntBiFunctionMemoizer<>(cache, keyFunction, toIntBiFunction);
    }

    /**
     * <p>
     * Memoizes a {@link ToIntBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param toIntBiFunction
     *            The {@link ToIntBiFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToIntBiFunction}.
     */
    public static final <FIRST, SECOND> ToIntBiFunction<FIRST, SECOND> toIntBiFunction(
            final ToIntBiFunction<FIRST, SECOND> toIntBiFunction,
            final Cache<String, Integer> cache) {
        return toIntBiFunction(toIntBiFunction, hashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param toIntFunction
     *            The {@link ToIntFunction} to memoize.
     * @return The wrapped {@link ToIntFunction}.
     */
    public static final <INPUT> ToIntFunction<INPUT> toIntFunction(
            final ToIntFunction<INPUT> toIntFunction) {
        return toIntFunction(toIntFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param toIntFunction
     *            The {@link ToIntFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToIntFunction}.
     */
    public static final <INPUT> ToIntFunction<INPUT> toIntFunction(
            final ToIntFunction<INPUT> toIntFunction,
            final Cache<INPUT, Integer> cache) {
        return toIntFunction(toIntFunction, identity(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param toIntFunction
     *            The {@link ToIntFunction} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @return The wrapped {@link ToIntFunction}.
     */
    public static final <INPUT, KEY> ToIntFunction<INPUT> toIntFunction(
            final ToIntFunction<INPUT> toIntFunction,
            final Function<INPUT, KEY> keyFunction) {
        return toIntFunction(toIntFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToIntFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param toIntFunction
     *            The {@link ToIntFunction} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToIntFunction}.
     */
    public static final <INPUT, KEY> ToIntFunction<INPUT> toIntFunction(
            final ToIntFunction<INPUT> toIntFunction,
            final Function<INPUT, KEY> keyFunction,
            final Cache<KEY, Integer> cache) {
        return new GuavaCacheBasedToIntFunctionMemoizer<>(cache, keyFunction, toIntFunction);
    }

    /**
     * <p>
     * Memoizes a {@link ToLongBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param toLongBiFunction
     *            The {@link ToLongBiFunction} to memoize.
     * @return The wrapped {@link ToLongBiFunction}.
     */
    public static final <FIRST, SECOND> ToLongBiFunction<FIRST, SECOND> toLongBiFunction(
            final ToLongBiFunction<FIRST, SECOND> toLongBiFunction) {
        return toLongBiFunction(toLongBiFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToLongBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param toLongBiFunction
     *            The {@link ToLongBiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @return The wrapped {@link ToLongBiFunction}.
     */
    public static final <FIRST, SECOND, KEY> ToLongBiFunction<FIRST, SECOND> toLongBiFunction(
            final ToLongBiFunction<FIRST, SECOND> toLongBiFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction) {
        return toLongBiFunction(toLongBiFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToLongBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param <KEY>
     *            The type of the cache key.
     * @param toLongBiFunction
     *            The {@link ToLongBiFunction} to memoize.
     * @param keyFunction
     *            The {@link BiFunction} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToLongBiFunction}.
     */
    public static final <FIRST, SECOND, KEY> ToLongBiFunction<FIRST, SECOND> toLongBiFunction(
            final ToLongBiFunction<FIRST, SECOND> toLongBiFunction,
            final BiFunction<FIRST, SECOND, KEY> keyFunction,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedToLongBiFunctionMemoizer<>(cache, keyFunction, toLongBiFunction);
    }

    /**
     * <p>
     * Memoizes a {@link ToLongBiFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <FIRST>
     *            The type of the first parameter.
     * @param <SECOND>
     *            The type of the second parameter.
     * @param toLongBiFunction
     *            The {@link ToLongBiFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToLongBiFunction}.
     */
    public static final <FIRST, SECOND> ToLongBiFunction<FIRST, SECOND> toLongBiFunction(
            final ToLongBiFunction<FIRST, SECOND> toLongBiFunction,
            final Cache<String, Long> cache) {
        return toLongBiFunction(toLongBiFunction, hashCodeKeyFunction(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param toLongFunction
     *            The {@link ToLongFunction} to memoize.
     * @return The wrapped {@link ToLongFunction}.
     */
    public static final <INPUT> ToLongFunction<INPUT> toLongFunction(
            final ToLongFunction<INPUT> toLongFunction) {
        return toLongFunction(toLongFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Default cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param toLongFunction
     *            The {@link ToLongFunction} to memoize.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToLongFunction}.
     */
    public static final <INPUT> ToLongFunction<INPUT> toLongFunction(
            final ToLongFunction<INPUT> toLongFunction,
            final Cache<INPUT, Long> cache) {
        return toLongFunction(toLongFunction, identity(), cache);
    }

    /**
     * <p>
     * Memoizes a {@link ToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Default cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param toLongFunction
     *            The {@link ToLongFunction} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @return The wrapped {@link ToLongFunction}.
     */
    public static final <INPUT, KEY> ToLongFunction<INPUT> toLongFunction(
            final ToLongFunction<INPUT> toLongFunction,
            final Function<INPUT, KEY> keyFunction) {
        return toLongFunction(toLongFunction, keyFunction, CacheBuilder.newBuilder().build());
    }

    /**
     * <p>
     * Memoizes a {@link ToLongFunction} in a Guava {@link Cache}.
     * </p>
     * <h3>Features</h3>
     * <ul>
     * <li>Custom cache</li>
     * <li>Custom cache key</li>
     * </ul>
     *
     * @param <INPUT>
     *            The type of the input.
     * @param <KEY>
     *            The type of the cache key.
     * @param toLongFunction
     *            The {@link ToLongFunction} to memoize.
     * @param keyFunction
     *            The {@link Function} to compute the cache key.
     * @param cache
     *            The {@link Cache} to use.
     * @return The wrapped {@link ToLongFunction}.
     */
    public static final <INPUT, KEY> ToLongFunction<INPUT> toLongFunction(
            final ToLongFunction<INPUT> toLongFunction,
            final Function<INPUT, KEY> keyFunction,
            final Cache<KEY, Long> cache) {
        return new GuavaCacheBasedToLongFunctionMemoizer<>(cache, keyFunction, toLongFunction);
    }

}
