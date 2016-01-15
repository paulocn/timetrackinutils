package ciandt.timetrackinutils.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ciandt.timetrackinutils.R;
import ciandt.timetrackinutils.storage.Constants;
import ciandt.timetrackinutils.storage.EncriptedSaver;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserConfigFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserConfigFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private EditText mEditTestUsername;
    private EditText mEditTestPassword;

    private Button mSaveButton;
    private Button mDeleteButton;

    // TODO: Rename and change types and number of parameters
    public static UserConfigFragment newInstance() {
        UserConfigFragment fragment = new UserConfigFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public UserConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_config, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mEditTestUsername =  (EditText) getView().findViewById(R.id.editTextUsername);
        mEditTestPassword =  (EditText) getView().findViewById(R.id.editTextPassword);
        mSaveButton  =  (Button) getView().findViewById(R.id.button_save);
        mDeleteButton  =  (Button) getView().findViewById(R.id.button_delete);

        mEditTestUsername.setText(EncriptedSaver.getDecripted(getActivity(), Constants.kUSERNAME));
        mEditTestPassword.setText(EncriptedSaver.getDecripted(getActivity(), Constants.kPASSWORD));


        mEditTestUsername.setImeActionLabel(getActivity().getString(R.string.next), KeyEvent.KEYCODE_ENTER);
        mEditTestPassword.setImeActionLabel(getActivity().getString(R.string.save), KeyEvent.KEYCODE_ENTER);

        //Listeners
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSavePassword(v);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionDeletePassword(v);
            }
        });

        mEditTestPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    actionSavePassword(v);
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void actionDeletePassword(View view) {
        EncriptedSaver.deleteEncripted(getActivity(), Constants.kPASSWORD);
        mEditTestUsername.setText(EncriptedSaver.getDecripted(getActivity(), Constants.kUSERNAME));
        mEditTestPassword.setText(EncriptedSaver.getDecripted(getActivity(), Constants.kPASSWORD));
    }

    public void actionSavePassword(View view) {

        String user = mEditTestUsername.getText().toString();
        String pass = mEditTestPassword.getText().toString();

        EncriptedSaver.saveEncripted(getActivity(), Constants.kUSERNAME, user);
        EncriptedSaver.saveEncripted(getActivity(), Constants.kPASSWORD, pass);

        Toast.makeText(getActivity(), getActivity().getString(R.string.saved), Toast.LENGTH_SHORT).show();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
