/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk;

import edu.polytechnique.labtk.exporter.ExportStrategy;
import edu.polytechnique.labtk.exporter.ResultExporter;
import edu.polytechnique.labtk.protocols.Protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Helper class providing convenient operations and factory methods for the
 * <em>Laboratory Toolkit</em>.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public final class LabUtils {

    /**
     * Creates a {@link java.util.concurrent.Callable} view of the parameterized
     * {@link Analysis} as used in the context of the specified
     * {@link Laboratory}. This does not have any impact on the parameterized
     * {@link Analysis} and {@link Laboratory} : it only creates a simple view
     * where both are implicit (therefore hidden). <p>Although the returned
     * result is a {@link java.util.concurrent.Callable}, it doesn't have
     * anything to do with concurrency, and unlike the signature might suggest,
     * it will not throw an {@link Exception}, since {@link Analysis}S don't
     * throw {@link Exception}s either. For that reason, it is recommended to
     * prefer the {@link LabUtils#asResultProducerForLab(Analysis, Laboratory)}
     * method for all situations but those where concurrency utilities are
     * actually involved; in which case it is critical to remember that the
     * returned {@link java.util.concurrent.Callable} will <em>not</em> be
     * Thread-safe.</p>
     * <p/>
     * <p><strong>Warning :</strong> it is strongly discouraged to use this
     * method for obtaining preliminary results for computing the result of an
     * {@link Analysis}, i.e in the body of the {@link Analysis#computeResult(ResultComputingContext)
     * } of an {@link Analysis} subclass. This is the purpose of a
     * {@link ResultComputingContext}. The <em>only</em> recommended use of this
     * method is for exporting a {@link java.util.concurrent.Callable} in which
     * the use of the
     * <em>Laboratory Toolkit</em> is hidden.</p>
     *
     * @param <R> the type of the result of the {@link Analysis}.
     * @param <L> the type of the {@link Laboratory}.
     * @param analysis the {@link Analysis} that computes the result of the
     * returned {@link java.util.concurrent.Callable}.
     * @param laboratory the {@link Laboratory} in the context of which the
     * result of the returned {@link java.util.concurrent.Callable} is computed.
     * @return a {@link java.util.concurrent.Callable} that uses the specified
     * {@link Analysis} and {@link Laboratory} to get its result.
     */
    public static <R, L extends Laboratory> Callable<R> asCallableForLab(final Analysis<? extends R, ? super L> analysis, final L laboratory) {
        if (analysis == null) {
            throw new NullPointerException("The requested " + Analysis.class.getSimpleName() + " is null.");
        }
        if (laboratory == null) {
            throw new NullPointerException("The requested " + Laboratory.class.getSimpleName() + " is null");
        }
        return new Callable<R>() {
            @Override
            public R call() throws Exception {
                return analysis.getResult(laboratory);
            }
        };
    }

    /**
     * Creates a {@link ResultProducer} view of the parameterized
     * {@link Analysis} as used in the context of the specified
     * {@link Laboratory}. This does not have any impact on the parameterized
     * {@link Analysis} and {@link Laboratory} : it only creates a simple view
     * where both are implicit (therefore hidden).
     * <p/>
     * <p><strong>Warning :</strong> it is strongly discouraged to use this
     * method for obtaining preliminary results for computing the result of an
     * {@link Analysis}, i.e in the body of the {@link Analysis#computeResult(ResultComputingContext)
     * } of an {@link Analysis} subclass. This is the purpose of a
     * {@link ResultComputingContext}. The <em>only</em> recommended use of this
     * method is for exporting a {@link ResultProducer} in which the use of the
     * <em>Laboratory Toolkit</em> is hidden.</p>
     *
     * @param <R> the type of the result of the {@link Analysis}.
     * @param <L> the type of the {@link Laboratory}.
     * @param analysis the {@link Analysis} that computes the result of the
     * returned {@link ResultProducer}.
     * @param laboratory the {@link Laboratory} in the context of which the
     * result of the returned {@link ResultProducer} is computed.
     * @return a {@link ResultProducer} that uses the specified {@link Analysis}
     * and {@link Laboratory} to get its result.
     */
    public static <R, L extends Laboratory> ResultProducer<R> asResultProducerForLab(final Analysis<? extends R, ? super L> analysis, final L laboratory) {
        if (analysis == null) {
            throw new NullPointerException("The requested " + Analysis.class.getSimpleName() + " is null.");
        }
        if (laboratory == null) {
            throw new NullPointerException("The requested " + Laboratory.class.getSimpleName() + " is null");
        }
        return new ResultProducer<R>() {
            @Override
            public R getResult() {
                return analysis.getResult(laboratory);
            }
        };
    }

    /**
     * Creates a new {@link ResultExporter} which source is the specified
     * {@link Laboratory}.
     * <p>Because of the subtleties of type inference in Java Generics, one
     * should be careful about the type parameter of the instantiated
     * {@link ResultExporter}. If the source {@link Laboratory} and the
     * destination {@link Laboratory}S do not have the same type, the type
     * parameter must be a common ancestor of all these types for the client
     * code to compile. The other {@link LabUtils#newResultExporter(Laboratory, Laboratory)
     * } helps avoiding this pitfall.</p>
     *
     * @param <E> the type of the equipment of the {@link Laboratory}S to export
     * from and import into.
     * @param source the {@link Laboratory} to export from.
     * @return a newly created {@link ResultExporter}.
     */
    public static <E> ResultExporter<E> newResultExporter(Laboratory<? extends E> source) {
        if (source == null) {
            throw new NullPointerException();
        }
        return new ResultExporterImpl<>(source);
    }

    /**
     * Creates a new {@link ResultExporter} which source is the specified
     * {@link Laboratory}, and is already set to import into the parameterized
     * destination {@link Laboratory}.
     *
     * @param <E> the type of the equipment of the {@link Laboratory}S to export
     * from and import into.
     * @param source the {@link Laboratory} to export from.
     * @param destination a {@link Laboratory} to import into.
     * @return a newly created {@link ResultExporter}.
     */
    public static <E> ResultExporter<E> newResultExporter(Laboratory<? extends E> source, Laboratory<? extends E> destination) {
        ResultExporterImpl<E> exporter = new ResultExporterImpl<>(source);
        exporter.addDestinationLab(destination);
        return exporter;
    }

    /**
     * Simple, default {@link ResultExporter} implementation.
     *
     * @param <E> the type of the equipment.
     */
    private static class ResultExporterImpl<E> implements ResultExporter<E> {

        /**
         * The {@link Laboratory} from which the results are exported.
         */
        private final Laboratory<? extends E> source;
        /**
         * The {@link Laboratory}S to which the result will be exported.
         */
        private final Collection<Laboratory<? extends E>> destinations = new ArrayList<>();
        /**
         * The {@link Analysis}S to export and their {@link ExportStrategy}.
         */
        private final Map<Analysis<?, ? super E>, ExportStrategy> analysisWithExportStrategies = new LinkedHashMap<>();
//        private final Collection<AnalysisWithExportStrategy<L>> analysisAndExportStrategies = new ArrayList<>();

        public ResultExporterImpl(Laboratory<? extends E> source) {
            if (source == null) {
                throw new NullPointerException();
            }
            this.source = source;
        }

        @Override
        public ResultExporter<E> addDestinationLab(Laboratory<? extends E> destination) {
            if (destination == null) {
                throw new NullPointerException();
            }
            this.destinations.add(destination);
            return this;
        }

        @Override
        public ResultExporter<E> exportResultOf(Analysis<?, ? super E> analysis) {
            if (analysis == null) {
                throw new NullPointerException();
            }
            return this.exportResultOf(analysis, ExportStrategy.COMPUTE_IF_NECESSARY);
        }

        @Override
        public ResultExporter<E> exportResultOf(Analysis<?, ? super E> analysis, ExportStrategy exportStrategy) {
            if (analysis == null) {
                throw new NullPointerException();
            }
            if (exportStrategy == null) {
                throw new NullPointerException();
            }
            this.analysisWithExportStrategies.put(analysis, exportStrategy);
            return this;
        }

        @Override
        public void export() {
            for (Map.Entry<Analysis<?, ? super E>, ExportStrategy> aWES : this.analysisWithExportStrategies.entrySet()) {
                Analysis<?, ? super E> analysis = aWES.getKey();
                switch (aWES.getValue()) {
                    case COMPUTE_IF_NECESSARY:
                        exportForAnalysis(analysis);
                        break;
                    case NO_EXPORT_IF_UNCOMPUTED:
                        if (source.isResultStoredFor(analysis)) {
                            exportForAnalysis(analysis);
                        }
                        break;
                }
            }
            this.clear();
        }

        private void clear() {
            this.analysisWithExportStrategies.clear();
            this.destinations.clear();
        }

        private void exportForAnalysis(Analysis<?, ? super E> analysis) {
            for (Laboratory<? extends E> destination : destinations) {
                destination.importResultFor(analysis.getResult(this.source), analysis);
            }
        }

        @Override
        public ResultExporter<E> addAllDestinaltionLabs(Iterable<Laboratory<? extends E>> destinations) {
            if (destinations == null) {
                throw new NullPointerException();
            }
            for (Laboratory<? extends E> destination : destinations) {
                this.addDestinationLab(destination);
            }
            return this;
        }

        @Override
        public ResultExporter<E> exportResultsOfAll(Iterable<? extends Analysis<?, ? super E>> analyses) {
            if (analyses == null) {
                throw new NullPointerException();
            }
            for (Analysis<?, ? super E> analysis : analyses) {
                this.exportResultOf(analysis);
            }
            return this;
        }

        @Override
        public ResultExporter<E> exportResultsOfAll(Iterable<? extends Analysis<?, ? super E>> analyses,
                ExportStrategy exportStrategy) {
            if (analyses == null) {
                throw new NullPointerException();
            }
            if (exportStrategy == null) {
                throw new NullPointerException();
            }
            for (Analysis<?, ? super E> analysis : analyses) {
                this.exportResultOf(analysis, exportStrategy);
            }
            return this;
        }
    }

    /**
     * Creates a trivial {@link Analysis} instance that always returns the
     * specified result.
     *
     * @param <R> the type of the result.
     * @param <E> the type of the equipment.
     * @param result the constant result to be returned by the newly created
     * {@link Analysis}. May be {@code null}.
     * @return a newly created {@link Analysis} that always directly returns the
     * parameterized result.
     */
    public static <R, E> Analysis<R, E> newConstantAnalysis(final R result) {
        return new Analysis<R, E>() {
            @Override
            protected R computeResult(ResultComputingContext<? extends E> context) {
                return result;
            }

            @Override
            public String toString() {
                return "constant Analysis [result=" + result + "]";
            }
        };
    }

    /**
     * Creates a {@link Protocol} always returns the specified result.
     *
     * @param <R> the type of the result.
     * @param <E> the type of the equipment.
     * @param result the constant result to be returned by the newly created
     * {@link Protocol}. May be {@code null}.
     * @return a newly created {@link Protocol} that always directly returns the
     * parameterized result.
     */
    public static <R, E> Protocol<R, E> newConstantProtocol(final R result) {
        return new Protocol<R, E>() {
            @Override
            public R computeResult(ResultComputingContext<? extends E> context) {
                return result;
            }

            @Override
            public String toString() {
                return "constant Protocol [result=" + result + "]";
            }
        };
    }
    /**
     * A {@link Protocol} that always returns {@code null}.
     */
    public static final Protocol NULL_RETURNING_PROTOCOL = newConstantProtocol(null);
    /**
     * An {@link Analysis} that always returns {@code null}.
     */
    public static final Analysis NULL_RETURNING_ANALYSIS = newConstantAnalysis(null);

    /**
     * Prevents instatiation.
     */
    private LabUtils() {
    }
}
