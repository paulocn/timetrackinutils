package ciandt.timetrackinutils.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.json.JSONObject;

import ciandt.timetrackinutils.R;
import ciandt.timetrackinutils.storage.ApontaSaver;
import ciandt.timetrackinutils.storage.MemoryStorageSingleton;
import ciandt.timetrackinutils.timetracking.TTAsyncRequest;
import ciandt.timetrackinutils.timetracking.TTCallbacks;
import ciandt.timetrackinutils.timetracking.TTRequester;
import ciandt.timetrackinutils.utils.notificacoes;
import ciandt.timetrackinutils.utils.utils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApontaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApontaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApontaFragment extends Fragment implements TTCallbacks{


    private OnFragmentInteractionListener mListener;

    private ImageView mImageRelogio;
    private ImageView mImagePonteiros;
    private ImageView mImagePonteiros2;

    private TextView mTxtLastAponta;

    private Button mApontaButton;

    private Animation mAnimationRotate;
    private Animation mAnimationRotateFast;


    // TODO: Rename and change types and number of parameters
    public static ApontaFragment newInstance() {
        ApontaFragment fragment = new ApontaFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public ApontaFragment() {
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
        return inflater.inflate(R.layout.fragment_aponta, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mImageRelogio =  (ImageView) getView().findViewById(R.id.imageView2);
        mImagePonteiros =  (ImageView) getView().findViewById(R.id.imageView3);
        mImagePonteiros2 =  (ImageView) getView().findViewById(R.id.imageView4);

        mApontaButton  =  (Button) getView().findViewById(R.id.button);

        mAnimationRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_center);
        mAnimationRotateFast = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_center_fast);

        mTxtLastAponta = (TextView) getView().findViewById(R.id.txt_lastapontamento);

        //Listeners
        mApontaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionApontar(v);
                iniciaAnimacaoApontamento();
            }
        });

        carregaTextoApontamento();

    }

    private void carregaTextoApontamento() {
        String ultimo = ApontaSaver.getLastNApontamentosStringyfied(1, getActivity()).get(0);

        mTxtLastAponta.setText(
                getString( R.string.ultimoapontamento) +
                " " +
                ultimo);
    }

    private void iniciaAnimacaoApontamento() {
        mApontaButton.setEnabled(false);
        mImagePonteiros.startAnimation(mAnimationRotateFast);
        mImagePonteiros2.startAnimation(mAnimationRotate);
        mApontaButton.getCompoundDrawables()[0].setAlpha(45);
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

    public void actionApontar(View view) {

        TTAsyncRequest req = new TTAsyncRequest(this);
        req.execute(MemoryStorageSingleton.getInstance().getUsername(getActivity()),
                MemoryStorageSingleton.getInstance().getPassword(getActivity()));

    }


    public void requestFinished(JSONObject responseJSON) {

        mImagePonteiros.clearAnimation();
        mImagePonteiros2.clearAnimation();
        mApontaButton.setEnabled(true);
        mApontaButton.getCompoundDrawables()[0].setAlpha(255);
        //Trocar strings hardcoded
        if (responseJSON != null) {

            String str = TTRequester.parseMessageFromTTJSON(responseJSON);

            String strs[] = str.split("\n");

            String msg = strs[0];
            if (strs.length > 1){
                msg = strs[1];
            }
            notificacoes.notifyOfAppointment(msg, getActivity());
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.timetracking)
                    .setMessage(msg).
                    setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                this.finalize();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    }).show();


            if ((str != null) && (!utils.checkForError(str))){
                ApontaSaver.addAndSaveDate(new DateTime(), getActivity());
            }


        }else{
            notificacoes.notifyOfAppointment(getActivity().getString(R.string.falhaApontamento), getActivity());
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.timetracking)
                    .setMessage(R.string.falhaApontamento).show();
        }



    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
