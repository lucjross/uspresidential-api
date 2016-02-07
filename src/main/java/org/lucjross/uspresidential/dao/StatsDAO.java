package org.lucjross.uspresidential.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by lucas on 2/2/16.
 */
public interface StatsDAO extends DAO<Object> {

    Map<Number, Map<String, Object>> getStatsByEvents(List<Integer> eventIds);
}
