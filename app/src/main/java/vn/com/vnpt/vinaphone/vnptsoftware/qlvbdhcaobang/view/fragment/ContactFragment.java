package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ContactAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ContactSearchAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ContactResultEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.KeywordEvent;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Realm realm;
    @BindView(R.id.txtNoData) TextView txtNoData;
    @BindView(R.id.edtKeyword) EditText edtKeyword;
    @BindView(R.id.layout_contact) LinearLayout layoutContact;
    @BindView(R.id.layoutDisplay) ScrollView layoutDisplay;
    private String keyword;
    private RealmResults<Contact> contacts;
    private List<Contact> contactList;

    private OnFragmentInteractionListener mListener;
    private Activity activityAttach;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ((MainActivity) activityAttach).hideNotify();
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(activityAttach.getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        edtKeyword.setTypeface(Application.getApp().getTypeface());
        edtKeyword.addTextChangedListener(
                new TextWatcher() {
                    private Timer timer = new Timer();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                activityAttach.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        keyword = edtKeyword.getText().toString();
                                        View view = activityAttach.getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) activityAttach.getSystemService(INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        search();
                                    }
                                });
                            }
                        }, Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        displayContact();
    }

    private void displayContact() {
        layoutContact.removeAllViewsInLayout();
        realm = RealmDao.with(this).getRealm();
        Map<String, String> mapFields = new HashMap<String, String>();
        mapFields.put("parentId", null);
        contacts = RealmDao.with(this).findFilterAnd(Contact.class, mapFields);
        if (contacts != null && contacts.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            List<Integer> sizes = new ArrayList<Integer>(contacts.size());
            List<Integer> countUsers = new ArrayList<Integer>(contacts.size());
            for (Contact contact : contacts) {
                mapFields = new HashMap<String, String>();
                mapFields.put("parentId", contact.getId());
                long count = RealmDao.with(this).countFilterAnd(Contact.class, mapFields);
                sizes.add((int) count);
                mapFields.put("isUser", "true");
                count = RealmDao.with(this).countFilterAnd(Contact.class, mapFields);
                countUsers.add((int) count);
            }
            ContactAdapter contactAdapter = new ContactAdapter(getContext(), R.layout.item_contact_list, R.layout.item_contact_detail, R.layout.item_contact_list_detail, contacts, sizes, countUsers);
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                layoutContact.addView(item);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void displaySearchContact() {
        layoutContact.removeAllViewsInLayout();
        List<Contact> lstParent = new ArrayList<>();
        EventBus.getDefault().postSticky(new ContactResultEvent(contactList));
        if (contactList != null && contactList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            for (int i = 0; i < contactList.size(); i++) {
                if (contactList.get(i).getParentId() == null) {
                    lstParent.add(contactList.get(i));
                }
            }
            List<Integer> sizes = new ArrayList<Integer>(lstParent.size());
            List<Integer> countUsers = new ArrayList<Integer>(lstParent.size());
            for (Contact contact : lstParent) {
                int count = 0;
                int countUser = 0;
                for (int i = 0; i < contactList.size(); i++) {
                    if (contactList.get(i).getParentId() != null && contactList.get(i).getParentId().equals(contact.getId())) {
                        count++;
                        if (contactList.get(i).getIsUser().equals("true")) {
                            countUser++;
                        }
                    }
                }
                sizes.add(count);
                countUsers.add(countUser);
            }
            ContactSearchAdapter contactAdapter = new ContactSearchAdapter(getContext(), R.layout.item_contact_list, R.layout.item_contact_detail, R.layout.item_contact_list_detail, lstParent, sizes, countUsers);
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                layoutContact.addView(item);
            }

            try {
                ViewGroup rootView = (ViewGroup) getView();
                int childViewCount = rootView.getChildCount();
                for (int i=0; i<childViewCount;i++){
                    View rootViewChildAt = rootView.getChildAt(i);
                }
            } catch (ClassCastException e){
                //Not a viewGroup here
            } catch (NullPointerException e){
                //Root view is null
            }

        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }


    private void search() {
        Map<String, String> mapFields = new HashMap<String, String>();
        mapFields.put("userName", keyword != null ? keyword.trim() : "");
        mapFields.put("position", keyword != null ? keyword.trim() : "");
        mapFields.put("unitName", keyword != null ? keyword.trim() : "");
        mapFields.put("phone", keyword != null ? keyword.trim() : "");
        mapFields.put("email", keyword != null ? keyword.trim() : "");
        RealmResults<Contact> contactFilter = RealmDao.with(this).findFilterOr(Contact.class, mapFields);
        contactList = realm.copyFromRealm(contactFilter);
        for (Contact contact : realm.copyFromRealm(contactFilter)) {
            searchParent(contact);
            searchChild(contact);
        }
        EventBus.getDefault().postSticky(new KeywordEvent(keyword != null ? keyword.trim() : ""));
        displaySearchContact();
    }

    private void searchChild(Contact contact) {
        Stack<Contact> stack = new Stack<>();
        stack.push(contact);
        while (stack.size() > 0) {
            Contact cont = stack.pop();
            if (!contactList.contains(cont)) {
                contactList.add(cont);
            }
            Map<String, String> mapFields = new HashMap<String, String>();
            mapFields.put("parentId", cont.getId());
            RealmResults<Contact> contactFilter = RealmDao.with(this).findFilterAnd(Contact.class, mapFields);
            if (contactFilter != null && contactFilter.size() > 0) {
                for (int i = 0; i < contactFilter.size(); i++) {
                    stack.push(realm.copyFromRealm(contactFilter.get(i)));
                }
            }
        }
    }

    private void searchParent(Contact contact) {
        if (!contactList.contains(contact)) {
            contactList.add(contact);
        }
        if (contact.getParentId() != null && !contact.getParentId().equals("")) {
            Contact contactParent = RealmDao.with(this).findByKey(Contact.class, contact.getParentId(), "id");
            if (contactParent != null) {
                searchParent(realm.copyFromRealm(contactParent));
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activityAttach = (Activity)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String init) {/* Init */};

}
