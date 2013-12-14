/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.exporter;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.Laboratory;

/**
 * A {@link ResultExporter} exposes a Builder-like API to export previously
 * computed {@link Analysis} results from a source {@link Laboratory} to a set
 * of destination {@link Laboratory}S, thus preventing the destination
 * {@link Laboratory}S from computing the results of these {@link Analysis}S
 * when requested.
 * <p/>
 * <p>This is typically useful in situations where the result of a low-level
 * {@link Analysis} is computed in a first {@link Laboratory}, then mutiple
 * variants of a high-level {@link Analysis} need to compute their results in
 * different {@link Laboratory}S, using the low-level result as a preliminary
 * result. This avoids recomputing the low-level result several times.</p>
 * <p/>
 * <p>However, a client importing results into a {@link Laboratory} should
 * proceed with caution. Depending on the design of the {@link Analysis}S
 * involved, this might typically lead the destination {@link Laboratory} to an
 * inconsistent state.</p>
 *
 * @param <E> the type of the equipment of the {@link Laboratory}S to import from and export to.
 *            This type must therefore be a common ancester of the source
 *            {@link Laboratory} equipment type and the destination {@link Laboratory}S types.
 */
public interface ResultExporter<E> {

    /**
     * Adds the specified {@link Laboratory} to the set of {@link Laboratory}S
     * the results will be imported into.
     *
     * @param destination a destination {@link Laboratory}.
     * @return this {@link ResultExporter}.
     */
    public ResultExporter<E> addDestinationLab(Laboratory<? extends E> lab);

    /**
     * Adds the specified {@link Laboratory}S to the set of {@link Laboratory}S
     * the results will be imported into.
     *
     * @param destinations destination {@link Laboratory}S.
     * @return this {@link ResultExporter}.
     */
    public ResultExporter<E> addAllDestinaltionLabs(Iterable<Laboratory<? extends E>> destinations);

    /**
     * Adds the specified {@link Analysis}S to the set of {@link Analysis}S
     * which results will be exported from the source {@link Laboratory} to the
     * destination {@link Laboratory}S.
     *
     * @param analysis an {@link Analysis} which result is to be exported.
     * @return this {@link ResultExporter}.
     */
    public ResultExporter<E> exportResultOf(Analysis<?, ? super E> analysis);

    /**
     * Adds the specified {@link Analysis}S to the set of {@link Analysis}S
     * which results will be exported from the source {@link Laboratory} to the
     * destination {@link Laboratory}S.
     *
     * @param analyses {@link Analysis} which result are to be exported.
     * @return this {@link ResultExporter}.
     */
    public ResultExporter<E> exportResultsOfAll(Iterable<? extends Analysis<?, ? super E>> analyses);

    /**
     * Adds the specified {@link Analysis}S to the set of {@link Analysis}S
     * which results will be exported from the source {@link Laboratory} to the
     * destination {@link Laboratory}S; depending on the specified
     * {@link ExportStrategy}, the result will be exported or not depending on
     * their computation state in the source {@link Laboratory}.
     *
     * @param analysis       an {@link Analysis} which result is to be exported.
     * @param exportStrategy decides on the export strategy. Default is
     *                       {@link ExportStrategy#COMPUTE_IF_NECESSARY}.
     * @return this {@link ResultExporter}.
     */
    public ResultExporter<E> exportResultOf(Analysis<?, ? super E> analysis, ExportStrategy exportStrategy);

    /**
     * Adds the specified {@link Analysis}S to the set of {@link Analysis}S
     * which results will be exported from the source {@link Laboratory} to the
     * destination {@link Laboratory}S; depending on the specified
     * {@link ExportStrategy}, the results will be exported or not depending on
     * their computation state in the source {@link Laboratory}.
     *
     * @param analyses       {@link Analysis} which result are to be exported.
     * @param exportStrategy decides on the export strategy. Default is
     *                       {@link ExportStrategy#COMPUTE_IF_NECESSARY}.
     * @return this {@link ResultExporter}.
     */
    public ResultExporter<E> exportResultsOfAll(Iterable<? extends Analysis<?, ? super E>> analyses,
                                                ExportStrategy exportStrategy);

    /**
     * Performs the exports, and clears this {@link ResultExporter} from its
     * destination {@link Laboratory}S and {@link Analysis}S, leaving it ready
     * to export again from its source {@link Laboratory}.
     */
    public void export();
}
