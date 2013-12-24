/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.queries;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import search.engine.equipment.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * {@link Analysis} creating the query runner that ranks documents for each
 * query.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ObtainingQueryRunner extends SimpleAnalysis<QueryRunner, QueryProcessingEquipment> {

    private static final Pattern QUERY_TOKENIZER = Pattern.compile("\\W+");

    private ObtainingQueryRunner() {
    }

    public static ObtainingQueryRunner getInstance() {
        return ObtainingQueryRunnerHolder.INSTANCE;
    }

    @Override
    protected QueryRunner computeResult(ResultComputingContext<? extends QueryProcessingEquipment> context) {
        final QueryProcessor queryProcessor = context.preliminaryResult(context.equipment().creatingQueryProcessor());
        final Set<Document> allDocuments = context.equipment().allDocuments();

        QueryRunner res = new QueryRunner() {
            @Override
            public List<Document> runSearchQuery(String query) {

                List<String> queryTerms = Arrays.asList(QUERY_TOKENIZER.split(query.toLowerCase()));
                final DocumentRanker ranker = queryProcessor.rankForQuery(queryTerms);

                //compares documents according to score
                Comparator<Document> rankingComparator = new Comparator<Document>() {
                    @Override
                    public int compare(Document o1, Document o2) {
                        double score1 = ranker.score(o1);
                        double score2 = ranker.score(o2);

                        return -Double.compare(score1, score2);
                    }
                };

                //sorting the documents by score for this query
                List<Document> rankedDocuments = new ArrayList<>(allDocuments);
                Collections.sort(rankedDocuments, rankingComparator);

                return Collections.unmodifiableList(rankedDocuments);
            }
        };

        logger.log(Level.INFO, "Created the query runner.");

        return res;
    }

    private static class ObtainingQueryRunnerHolder {

        private static final ObtainingQueryRunner INSTANCE = new ObtainingQueryRunner();
    }

    private static final Logger logger = Logger.getLogger(ObtainingQueryRunner.class.getName());
}
