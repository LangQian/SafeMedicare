package course1778.mobileapp.safeMedicare.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import course1778.mobileapp.safeMedicare.Helpers.DatabaseHelper;
import course1778.mobileapp.safeMedicare.Helpers.Helpers;
import course1778.mobileapp.safeMedicare.NotificationService.Alarm;
import course1778.mobileapp.safeMedicare.R;

/**
 * Created by jianhuang on 16-03-03.
 */
public class PatientFrag extends android.support.v4.app.Fragment {

    public TextView todos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_frag,
                container, false);

        todos = (TextView) view.findViewById(R.id.todos);

        // onBackPress key listener
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), WelcomePage.class);
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_patient, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                retrieveDataFromParse();
                break;
        }

        return (super.onOptionsItemSelected(item));
    }

    public void retrieveDataFromParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Helpers.PARSE_OBJECT);
        query.whereEqualTo(Helpers.PARSE_OBJECT_USER, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseObjectList, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : parseObjectList) {
                        Bundle bundle = new Bundle();
                        bundle.putString(DatabaseHelper.TITLE, parseObject.getString(DatabaseHelper.TITLE));
                        bundle.putString(DatabaseHelper.TITLE, parseObject.getString(DatabaseHelper.TIME_H));
                        bundle.putString(DatabaseHelper.TITLE, parseObject.getString(DatabaseHelper.TIME_M));

                        Log.d("mybundle","TITLE: " + bundle.getString(DatabaseHelper.TITLE));
                        Log.d("mybundle", "HOUR: " +bundle.getString(DatabaseHelper.TIME_H));
                        Log.d("mybundle","MIN: " + bundle.getString(DatabaseHelper.TIME_M));

                        Alarm alarm = new Alarm(getActivity().getApplicationContext(), bundle);
                    }
//                    todos.setText("");
//                    for (int i = 0; i < parseObjectList.size(); i++) {
//                        todos.append("\n");
//                        todos.append(parseObjectList.get(i).getString(DatabaseHelper.TITLE));
//                        todos.append(" ");
//                        todos.append(parseObjectList.get(i).getString(DatabaseHelper.TIME_H));
//                        todos.append(" ");
//                        todos.append(parseObjectList.get(i).getString(DatabaseHelper.TIME_M));
//                    }
                }
            }
        });
    }

    public void syncParseObject(ParseObject parseObject) {
        parseObject.fetchInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("myupdate", "updated");
                } else {
                    Log.d("myupdate", "failed to update");
                }
            }
        });
    }
}
