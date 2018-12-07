package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.PersonSendDoc;

/**
 * Created by VietNH on 8/31/2017.
 */

public class PersonSendDocAdapter extends ArrayAdapter<PersonSendDoc> {
    private Context context;
    private int resource;
    private List<PersonSendDoc> personSendDocList;

    public PersonSendDocAdapter(Context context, int resource, List<PersonSendDoc> personSendDocList) {
        super(context, resource, personSendDocList);
        this.context = context;
        this.resource = resource;
        this.personSendDocList = personSendDocList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final PersonSendDoc personSendDoc = personSendDocList.get(position);
        final LinearLayout itemPersons = (LinearLayout) view.findViewById(R.id.itemPersons);
        final TextView txtName = (TextView) view.findViewById(R.id.txtName);
        final ImageView btnRemove = (ImageView) view.findViewById(R.id.btnRemove);
        txtName.setText(personSendDoc.getName());
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                if (personSendDoc.getType().equals("LIENTHONG_LIST")) {
                    List<Person> personList = listPersonEvent.getPersonLienThongList();
                    for (Person person : personList) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            personList.remove(person);
                            break;
                        }
                    }
                    listPersonEvent.setPersonLienThongList(personList);
                    EventBus.getDefault().postSticky(listPersonEvent);
                }
                if (personSendDoc.getType().equals("PROCESS_LIST")) {
                    List<Person> personList = listPersonEvent.getPersonProcessList();
                    for (Person person : personList) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            personList.remove(person);
                            break;
                        }
                    }
                    listPersonEvent.setPersonLienThongList(personList);
                    EventBus.getDefault().postSticky(listPersonEvent);
                }
                if (personSendDoc.getType().equals("RIDRECT_LIST")) {
                    List<Person> personList = listPersonEvent.getPersonDirectList();
                    for (Person person : personList) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            personList.remove(person);
                            break;
                        }
                    }
                    listPersonEvent.setPersonDirectList(personList);
                    EventBus.getDefault().postSticky(listPersonEvent);
                }
                if (personSendDoc.getType().equals("NOTIFY_LIST")) {
                    List<Person> personList = listPersonEvent.getPersonNotifyList();
                    for (Person person : personList) {
                        if (person.getId().equals(personSendDoc.getId())) {
                            personList.remove(person);
                            break;
                        }
                    }
                    listPersonEvent.setPersonNotifyList(personList);
                    EventBus.getDefault().postSticky(listPersonEvent);
                }
                itemPersons.setVisibility(View.GONE);
            }
        });
        return view;
    }
}
