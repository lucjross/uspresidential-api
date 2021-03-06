package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.StatsDAO;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lucas on 2/2/16.
 */
@Repository
public class StatsDAOImpl
        extends AbstractDAO<Object>
        implements StatsDAO {

    private static final String STATS_BY_EVENT_SQL = "select\n" +
            "  event_id,\n" +
            "  sum(case when votes.response = 'YES'            then 1 else 0 end) / count(*) as distribution_YES,\n" +
            "  sum(case when votes.response = 'NO'             then 1 else 0 end) / count(*) as distribution_NO,\n" +
            "  sum(case when votes.response = 'UNKNOWN'        then 1 else 0 end) / count(*) as distribution_UNKNOWN,\n" +
            "  sum(case when votes.response = 'NOT_IMPORTANT'  then 1 else 0 end) / count(*) as distribution_NOT_IMPORTANT,\n" +
            "  sum(votes.voteWeight) / count(*) as avgWeight\n" +
            "from votes\n" +
            "where event_id in (%s)\n" +
            "group by event_id";

    @Override
    public Map<Number, Map<String, Object>> getStatsByEvents(List<Integer> eventIds) {

        String params = String.join(",", Collections.nCopies(eventIds.size(), "?"));
        String sql = String.format(STATS_BY_EVENT_SQL, params);
        List<Map<String, Object>> list = jdbcOps.queryForList(sql, eventIds.toArray());
        return list.stream().collect(
                Collectors.toMap(map -> (Number) map.get("event_id"), Function.identity()));
    }
}
