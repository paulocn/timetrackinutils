package ciandt.timetrackinutils.timetracking;

import org.json.JSONObject;

/**
 * Created by paulocn on 05/10/15.
 */
public interface TTCallbacks {
    public void requestFinished (JSONObject responseJSON);
}
