package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonSendNotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonSendNotifyCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonSendNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.PersonSendNotifyCheck;

/**
 * Created by VietNH on 8/31/2017.
 */

public class SelectSendXemDBAdapter extends ArrayAdapter<PersonSendNotifyInfo> {
    private Context context;
    private int resource;
    private int resourceDetail;
    private List<PersonSendNotifyInfo> personSendNotifyInfoList;
    private List<Boolean> touchs;
    private List<Integer> sizes;

    public SelectSendXemDBAdapter(Context context, int resource, int resourceDetail, List<PersonSendNotifyInfo> personSendNotifyInfoList, List<Integer> sizes, List<Boolean> touchs) {
        super(context, resource, personSendNotifyInfoList);
        this.context = context;
        this.resource = resource;
        this.resourceDetail = resourceDetail;
        this.personSendNotifyInfoList = personSendNotifyInfoList;
        this.sizes = sizes;
        this.touchs = touchs;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        List<Person> personList = new ArrayList<>();
        List<Person> personGroupList = new ArrayList<>();
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            personList = listPersonEvent.getPersonNotifyList();
            personGroupList = listPersonEvent.getPersonGroupList();
        }
        boolean notParent = true;
        if (sizes.get(position) > 0) {
            view = inflater.inflate(this.resource, null);
            notParent = false;
            final TextView txtNameDonvi = (TextView) view.findViewById(R.id.txtNameDonvi);
            final CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
            txtNameDonvi.setTypeface(Application.getApp().getTypeface());
            txtNameDonvi.setText(personSendNotifyInfoList.get(position).getName());
            if (personList != null && personList.size() > 0) {
                for (Person person : personList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXem()) {
                            checkXemDB.setChecked(true);
                        }
                    }
                }
            }
            if (personGroupList != null && personGroupList.size() > 0) {
                for (Person person : personGroupList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXem()) {
                            checkXemDB.setChecked(true);
                        }
                    }
                }
            }
            final LinearLayout layoutPersonDonvi = (LinearLayout) view.findViewById(R.id.personDonvi);
            if (!touchs.get(position)) {
                layoutPersonDonvi.setPadding(40, 0, 0, 0);
                txtNameDonvi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                layoutPersonDonvi.setVisibility(View.VISIBLE);
                touchs.set(position, true);
                List<PersonSendNotifyInfo> lst = new ArrayList<>();
                List<PersonSendNotifyInfo> personSendNotifyInfos = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                for (int i = 0; i < personSendNotifyInfos.size(); i++) {
                    if (personSendNotifyInfoList.get(position).getId().equals(personSendNotifyInfos.get(i).getParentId())) {
                        lst.add(personSendNotifyInfos.get(i));
                    }
                }
                List<Integer> sizes = new ArrayList<Integer>(lst.size());
                List<Boolean> touchs = new ArrayList<>();
                for (int i = 0; i < lst.size(); i++) {
                    int count = 0;
                    for (int j = 0; j < personSendNotifyInfos.size(); j++) {
                        if (lst.get(i).getId().equals(personSendNotifyInfos.get(j).getParentId())) {
                            count++;
                        }
                    }
                    sizes.add(count);
                    touchs.add(false);
                }
                SelectSendXemDBAdapter selectSendNotifyAdapter = new SelectSendXemDBAdapter(context, R.layout.item_person_send_xemdb_list, R.layout.item_person_send_xemdb_detail, lst, sizes, touchs);
                int adapterCount = selectSendNotifyAdapter.getCount();
                for (int i = 0; i < adapterCount; i++) {
                    View item = selectSendNotifyAdapter.getView(i, null, null);
                    layoutPersonDonvi.addView(item);
                }
            }
            txtNameDonvi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!touchs.get(position)) {
                        txtNameDonvi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                        layoutPersonDonvi.setVisibility(View.VISIBLE);
                        touchs.set(position, true);
                    } else {
                        txtNameDonvi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                        layoutPersonDonvi.setVisibility(View.GONE);
                        touchs.set(position, false);
                    }
                }
            });
            checkXemDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkXemDB.isChecked()) {
//                        List<PersonSendNotifyInfo> lst = new ArrayList<PersonSendNotifyInfo>();
//                        List<PersonSendNotifyInfo> personSendNotifyInfos = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
//                        Stack<PersonSendNotifyInfo> stack = new Stack<>();
//                        stack.push(personSendNotifyInfoList.get(position));
//                        while (stack.size() > 0) {
//                            PersonSendNotifyInfo cont = stack.pop();
//                            if (!lst.contains(cont)) {
//                                lst.add(cont);
//                            }
//                            for (int i = 0; i < personSendNotifyInfos.size(); i++) {
//                                if (cont.getId().equals(personSendNotifyInfos.get(i).getParentId())) {
//                                    stack.push(personSendNotifyInfos.get(i));
//                                }
//                            }
//                        }

                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                check.setChecked(true);
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    } else {
//                        List<PersonSendNotifyInfo> lst = new ArrayList<PersonSendNotifyInfo>();
//                        List<PersonSendNotifyInfo> personSendNotifyInfos = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
//                        Stack<PersonSendNotifyInfo> stack = new Stack<>();
//                        stack.push(personSendNotifyInfoList.get(position));
//                        while (stack.size() > 0) {
//                            PersonSendNotifyInfo cont = stack.pop();
//                            if (!lst.contains(cont)) {
//                                lst.add(cont);
//                            }
//                            for (int i = 0; i < personSendNotifyInfos.size(); i++) {
//                                if (cont.getId().equals(personSendNotifyInfos.get(i).getParentId())) {
//                                    stack.push(personSendNotifyInfos.get(i));
//                                }
//                            }
//                        }
//                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
//                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
//                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
//                            for (int j = 0; j < lst.size(); j++) {
//                                if (personSendNotifyChecks.get(i).getId().equals(lst.get(j).getId())) {
//                                    View view1 = personSendNotifyChecks.get(i).getView();
//                                    CheckBox check = (CheckBox) view1.findViewById(R.id.checkXLChinh);
//                                    check.setChecked(false);
//                                    personSendNotifyChecks.get(i).setView(view1);
//                                    break;
//                                }
//                            }
//                        }
//
//                        lst = new ArrayList<PersonSendNotifyInfo>();
//                        stack = new Stack<>();
//                        stack.push(personSendNotifyInfoList.get(position));
//                        while (stack.size() > 0) {
//                            PersonSendNotifyInfo cont = stack.pop();
//                            if (!lst.contains(cont)) {
//                                lst.add(cont);
//                            }
//                            for (int i = 0; i < personSendNotifyInfos.size(); i++) {
//                                if (personSendNotifyInfos.get(i).getId().equals(cont.getParentId())) {
//                                    stack.push(personSendNotifyInfos.get(i));
//                                }
//                            }
//                        }
//                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
//                            for (int j = 0; j < lst.size(); j++) {
//                                if (personSendNotifyChecks.get(i).getId().equals(lst.get(j).getId())) {
//                                    View view1 = personSendNotifyChecks.get(i).getView();
//                                    CheckBox check = (CheckBox) view1.findViewById(R.id.checkXLChinh);
//                                    check.setChecked(false);
//                                    personSendNotifyChecks.get(i).setView(view1);
//                                    break;
//                                }
//                            }
//                        }
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                check.setChecked(false);
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
        } else {
            view = inflater.inflate(this.resourceDetail, null);
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            final CheckBox checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
            txtName.setTypeface(Application.getApp().getTypeface());
            txtName.setText(personSendNotifyInfoList.get(position).getName());
            if (personList != null && personList.size() > 0) {
                for (Person person : personList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXem()) {
                            checkXemDB.setChecked(true);
                        }
                    }
                }
            }
            if (personGroupList != null && personGroupList.size() > 0) {
                for (Person person : personGroupList) {
                    if (personSendNotifyInfoList.get(position).getId().equals(person.getId())) {
                        if (person.isXem()) {
                            checkXemDB.setChecked(true);
                        }
                    }
                }
            }
            checkXemDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkXemDB.isChecked()) {
//                        List<PersonSendNotifyInfo> lst = new ArrayList<PersonSendNotifyInfo>();
//                        List<PersonSendNotifyInfo> personSendNotifyInfos = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
//                        Stack<PersonSendNotifyInfo> stack = new Stack<>();
//                        stack.push(personSendNotifyInfoList.get(position));
//                        while (stack.size() > 0) {
//                            PersonSendNotifyInfo cont = stack.pop();
//                            if (!lst.contains(cont)) {
//                                lst.add(cont);
//                            }
//                            for (int i = 0; i < personSendNotifyInfos.size(); i++) {
//                                if (personSendNotifyInfos.get(i).getId().equals(cont.getParentId())) {
//                                    stack.push(personSendNotifyInfos.get(i));
//                                }
//                            }
//                        }
                        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
                        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
                        for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                            if (personSendNotifyChecks.get(i).getId().equals(personSendNotifyInfoList.get(position).getId())) {
                                View view1 = personSendNotifyChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkXemDB);
                                check.setChecked(false);
                                personSendNotifyChecks.get(i).setView(view1);
                                break;
                            }
                        }
                        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
                        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
                    }
                }
            });
        }
        PersonSendNotifyCheck personSendNotifyCheck = new PersonSendNotifyCheck(view, personSendNotifyInfoList.get(position).getId(), personSendNotifyInfoList.get(position).getParentId(),
                                                                    personSendNotifyInfoList.get(position).getName(), null, notParent);
        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
        personSendNotifyChecks.add(personSendNotifyCheck);
        personSendNotifyCheckEvent.setPersonSendNotifyCheckList(personSendNotifyChecks);
        EventBus.getDefault().postSticky(personSendNotifyCheckEvent);
        return view;
    }

}

