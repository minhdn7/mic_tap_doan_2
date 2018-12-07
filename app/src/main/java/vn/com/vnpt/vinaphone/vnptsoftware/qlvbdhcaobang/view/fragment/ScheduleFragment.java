package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.builder.ScheduleBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Event;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Schedule;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule.ISchedulePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule.SchedulePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailScheduleActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IScheduleView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment implements ToolTipView.OnToolTipViewClickedListener, MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener, IScheduleView, ILoginView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.weekView) WeekView mWeekView;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    @BindView(R.id.sSchedule) Spinner sSchedule;
    @BindView(R.id.tv_schedule_label) TextView tv_schedule_label;
    @BindView(R.id.tv_tuan_label) TextView tv_tuan_label;
    @BindView(R.id.tv_tuan) TextView tv_tuan;
    @BindView(R.id.nextWeek) ImageView nextWeek;
    @BindView(R.id.backWeek) ImageView backWeek;
    @BindView(R.id.fab) FloatingActionButton addSchedule;
    @BindView(R.id.txtGio) TextView txtGio;
    private ProgressDialog progressDialog;
    private String type;
    private int isCurrent = 0;
    private Calendar firstDayOfWeek;
    private Calendar lastDayOfWeek;
    private Calendar weekend;
    private SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private ToolTipView mOrangeToolTipView;
    private ToolTipRelativeLayout mToolTipFrameLayout;

    private OnFragmentInteractionListener mListener;
    private Activity activityAttach;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);
        mToolTipFrameLayout = (ToolTipRelativeLayout) view.findViewById(R.id.activity_main_tooltipframelayout);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setEventLongPressListener(this);
        init();
        setupDateTimeInterpreter(false);
        listenerTypeSchedule();
//        RealmResults<Schedule> schedules = RealmDao.with(this).findByDate(Schedule.class, "startTime", "endTime", firstDayOfWeek.getTime(), weekend.getTime());
//        displaySchedule(schedules);
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getWeekSchedules(new ScheduleRequest(sdf.format(firstDayOfWeek.getTime()), sdf.format(weekend.getTime())));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
        return view;
    }

//    private void displaySchedule(RealmResults<Schedule> schedules) {
//        events = new ArrayList<>();
//        for (Schedule schedule : schedules) {
//            if (schedule.getType().equals(type)) {
//                Calendar from = dateToCalendar(schedule.getStartTime());
//                Calendar to = dateToCalendar(schedule.getEndTime());
//                if (from.get(Calendar.DATE) == to.get(Calendar.DATE)) {
//                    Event event = new Event();
//                    event.setId(schedule.getId());
//                    event.setStartTime(schedule.getStartTime());
//                    event.setEndTime(schedule.getEndTime());
//                    String title = schedule.getTitle() != null && schedule.getTitle().length() > 20 ? schedule.getTitle().substring(0, 20) + "..."  : schedule.getTitle();
//                    String position = schedule.getPosition() != null && schedule.getPosition().length() > 12 ? schedule.getPosition().substring(0, 12) + "..." : schedule.getPosition();
//                    String chiTri = schedule.getChuTri() != null && schedule.getChuTri().length() > 15 ? schedule.getChuTri().substring(0, 15) + "..." : schedule.getChuTri();
//                    event.setName(title + "\n" + position + "\n" + chiTri);
//                    event.setColor("#00aeef");
//                    event.setFullname(schedule.getTitle() + "\n" + schedule.getPosition() + "\n" + schedule.getChuTri());
//                    event.setType(schedule.getType());
//                    events.add(event.toWeekViewEvent(from));
//                } else {
//                    from = maxCalendars(from, firstDayOfWeek);
//                    to = minCalendars(to, weekend);
//                    Calendar calendar = (Calendar) from.clone();
//                    while (calendar.get(Calendar.DATE) <= to.get(Calendar.DATE)) {
//                        Event event = new Event();
//                        event.setId(schedule.getId());
//                        event.setType(schedule.getType());
//                        String title = schedule.getTitle() != null && schedule.getTitle().length() > 20 ? schedule.getTitle().substring(0, 20) + "..."  : schedule.getTitle();
//                        String position = schedule.getPosition() != null && schedule.getPosition().length() > 12 ? schedule.getPosition().substring(0, 12) + "..." : schedule.getPosition();
//                        String chiTri = schedule.getChuTri() != null && schedule.getChuTri().length() > 15 ? schedule.getChuTri().substring(0, 15) + "..." : schedule.getChuTri();
//                        event.setName(title + "\n" + position + "\n" + chiTri);
//                        event.setColor("#00aeef");
//                        event.setFullname(schedule.getTitle() + "\n" + schedule.getPosition() + "\n" + schedule.getChuTri());
//                        if (calendar.get(Calendar.DATE) == from.get(Calendar.DATE)) {
//                            event.setStartTime(from.getTime());
//                        } else {
//                            calendar.set(Calendar.HOUR_OF_DAY, 8);
//                            event.setStartTime(calendar.getTime());
//                        }
//                        if (calendar.get(Calendar.DATE) == to.get(Calendar.DATE)) {
//                            event.setEndTime(to.getTime());
//                        } else {
//                            calendar.set(Calendar.HOUR_OF_DAY, 17);
//                            event.setEndTime(calendar.getTime());
//                        }
//                        events.add(event.toWeekViewEvent(calendar));
//                        calendar.add(Calendar.DATE, 1);
//                    }
//                }
//            }
//        }
//        mWeekView.notifyDatasetChanged();
//    }

    private void displaySchedule(List<Schedule> schedules) {
        events = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getType().equals(type)) {
                Calendar from = dateToCalendar(schedule.getStartTime());
                Calendar to = dateToCalendar(schedule.getEndTime());
                if (checkDuplicate(from, to)) {
                    Event event = new Event();
                    event.setId(schedule.getId());
                    event.setStartTime(schedule.getStartTime());
                    event.setEndTime(schedule.getEndTime());
                    String title = schedule.getTitle() != null && schedule.getTitle().length() > 20 ? schedule.getTitle().substring(0, 20) + "..."  : schedule.getTitle();
                    String position = schedule.getPosition() != null && schedule.getPosition().length() > 12 ? schedule.getPosition().substring(0, 12) + "..." : schedule.getPosition();
                    String chiTri = schedule.getChuTri() != null && schedule.getChuTri().length() > 15 ? schedule.getChuTri().substring(0, 15) + "..." : schedule.getChuTri();
                    event.setName(title + "\n" + position + "\n" + chiTri);
                    event.setColor("#00aeef");
                    event.setType(schedule.getType());
                    event.setFullname(schedule.getTitle() + "\n" + schedule.getPosition() + "\n" + schedule.getChuTri());
                    events.add(event.toWeekViewEvent(from));
                } else {
                    from = maxCalendars(from, firstDayOfWeek);
                    to = minCalendars(to, weekend);
                    Calendar calendar = (Calendar) from.clone();
                    do {
                        Event event = new Event();
                        event.setId(schedule.getId());
                        event.setType(schedule.getType());
                        String title = schedule.getTitle() != null && schedule.getTitle().length() > 20 ? schedule.getTitle().substring(0, 20) + "..."  : schedule.getTitle();
                        String position = schedule.getPosition() != null && schedule.getPosition().length() > 12 ? schedule.getPosition().substring(0, 12) + "..." : schedule.getPosition();
                        String chiTri = schedule.getChuTri() != null && schedule.getChuTri().length() > 15 ? schedule.getChuTri().substring(0, 15) + "..." : schedule.getChuTri();
                        event.setName(title + "\n" + position + "\n" + chiTri);
                        event.setColor("#00aeef");
                        event.setFullname(schedule.getTitle() + "\n" + schedule.getPosition() + "\n" + schedule.getChuTri());
                        if (calendar.get(Calendar.DATE) == from.get(Calendar.DATE)) {
                            event.setStartTime(from.getTime());
                        } else {
                            calendar.set(Calendar.HOUR_OF_DAY, 8);
                            event.setStartTime(calendar.getTime());
                        }
                        if (calendar.get(Calendar.DATE) == to.get(Calendar.DATE)) {
                            event.setEndTime(to.getTime());
                        } else {
                            calendar.set(Calendar.HOUR_OF_DAY, 17);
                            event.setEndTime(calendar.getTime());
                        }
                        events.add(event.toWeekViewEvent(calendar));
                        calendar.add(Calendar.DATE, 1);
                    } while (!calendar.after(to));
                }
            }
        }
        mWeekView.notifyDatasetChanged();
    }

    private void init() {
        connectionDetector = new ConnectionDetector(getContext());
        type = Constants.SCHEDULE_BUSSINESS;
        EventBus.getDefault().postSticky(new InitEvent(true));
        tv_schedule_label.setTypeface(Application.getApp().getTypeface());
        tv_tuan_label.setTypeface(Application.getApp().getTypeface());
        txtGio.setTypeface(Application.getApp().getTypeface());
        tv_tuan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_tuan.setTextColor(Color.parseColor("#00aeef"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.string_array_schedule_type)) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.colorHint));
                return v;
            }
        };
        ((MainActivity) activityAttach).hideNotify();
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sSchedule.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sSchedule.setSelection(0);
        firstDayOfWeek = Calendar.getInstance();
        while (firstDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            firstDayOfWeek.add(Calendar.DATE, -1);
        }
        firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 8);
        firstDayOfWeek.set(Calendar.MINUTE, 0);
        lastDayOfWeek = Calendar.getInstance();
        if (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            lastDayOfWeek.add(Calendar.DATE, -1);
        } else {
            if (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                lastDayOfWeek.add(Calendar.DATE, -2);
            } else {
                while (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                    lastDayOfWeek.add(Calendar.DATE, 1);
                }
            }
        }
        lastDayOfWeek.set(Calendar.HOUR_OF_DAY, 17);
        lastDayOfWeek.set(Calendar.MINUTE, 0);
        weekend = (Calendar) lastDayOfWeek.clone();
        weekend.add(Calendar.DATE, 2);
        tv_tuan.setText(sdf.format(firstDayOfWeek.getTime()) + " - " + sdf.format(lastDayOfWeek.getTime()));
    }

    private void listenerTypeSchedule() {
        sSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    type = Constants.SCHEDULE_BUSSINESS;
                } else {
                    type = Constants.SCHEDULE_MEETING;
                }
                if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
                    EventBus.getDefault().postSticky(new InitEvent(false));
                } else {
                    setupDateTimeInterpreter(false);
                    search();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void search() {
        weekend = (Calendar) lastDayOfWeek.clone();
        weekend.add(Calendar.DATE, 2);
        if (isCurrent == 0) {
            tv_tuan.setTextColor(getResources().getColor(R.color.backgroundBlue));
//            RealmResults<Schedule> schedules = RealmDao.with(this).findByDate(Schedule.class, "startTime", "endTime", firstDayOfWeek.getTime(), weekend.getTime());
//            displaySchedule(schedules);
        } else {
            tv_tuan.setTextColor(getResources().getColor(R.color.md_black));
        }
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getWeekSchedules(new ScheduleRequest(sdf.format(firstDayOfWeek.getTime()), sdf.format(weekend.getTime())));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @OnClick({R.id.nextWeek, R.id.backWeek})
    public void onWeekChange(View view) {
        switch (view.getId()) {
            case R.id.nextWeek:
                isCurrent++;
                firstDayOfWeek.add(Calendar.DATE, 7);
                lastDayOfWeek.add(Calendar.DATE, 7);
                firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 8);
                firstDayOfWeek.set(Calendar.MINUTE, 0);
                tv_tuan.setText(sdf.format(firstDayOfWeek.getTime()) + " - " + sdf.format(lastDayOfWeek.getTime()));
                break;
            case R.id.backWeek:
                isCurrent--;
                firstDayOfWeek.add(Calendar.DATE, -7);
                lastDayOfWeek.add(Calendar.DATE, -7);
                firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 8);
                firstDayOfWeek.set(Calendar.MINUTE, 0);
                tv_tuan.setText(sdf.format(firstDayOfWeek.getTime()) + " - " + sdf.format(lastDayOfWeek.getTime()));
                break;
        }
        weekend = (Calendar) lastDayOfWeek.clone();
        weekend.add(Calendar.DATE, 2);
        setupDateTimeInterpreter(false);
        search();
    }

    @OnClick({R.id.fab})
    public void onAddSchedule(View view) {
        switch (view.getId()) {
            case R.id.fab:
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.FUNCTION_NO_SUPPORT_INFO), true, AlertDialogManager.INFO);
                break;
        }
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setNumberOfVisibleDays(Constants.SCHEDULE_NUMBER_DAY_OF_WEEK_DEFAULT);
        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
        mWeekView.setOverlappingEventGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, getResources().getDisplayMetrics()));
        mWeekView.goToDate(firstDayOfWeek);
        mWeekView.goToHour(Constants.SCHEDULE_HOUR_DEFAULT);
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate) {
                    weekday = String.valueOf(weekday.charAt(0));
                }
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour + ":00";
            }

        });
        firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 8);
        firstDayOfWeek.set(Calendar.MINUTE, 0);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) + 1 == month) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) + 1 == month);
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
        hideProgress();
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        EventBus.getDefault().postSticky(event);
        EventBus.getDefault().postSticky(new StepPre(Constants.SCHEDULE_LIST));
        activityAttach.startActivity(new Intent(activityAttach, DetailScheduleActivity.class));
    }

    @Override
    public void onSuccess(List<ScheduleInfo> scheduleInfoList) {
        List<Schedule> schedules = new ArrayList<>();
        if (scheduleInfoList != null && scheduleInfoList.size() > 0) {
            ScheduleBuilder scheduleBuilder = new ScheduleBuilder(getContext());
            schedules = scheduleBuilder.convertFromScheduleInfo(scheduleInfoList);
        }
        firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 8);
        firstDayOfWeek.set(Calendar.MINUTE, 0);
        displaySchedule(schedules);
    }

    @Override
    public void onSuccessBoss(List<ScheduleBossInfo> schedules) {

    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getWeekSchedules(new ScheduleRequest(sdf.format(firstDayOfWeek.getTime()), sdf.format(lastDayOfWeek.getTime())));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(activityAttach, LoginActivity.class));
        activityAttach.finish();
    }

    @Override
    public void showProgress() {
        if (!activityAttach.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activityAttach);
                progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
            }

            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (!activityAttach.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        ToolTip toolTip = new ToolTip()
                .withText(event.getName())
                .withColor(getResources().getColor(R.color.md_blue_800));
        mToolTipFrameLayout.setMinimumWidth(100);
        mOrangeToolTipView = mToolTipFrameLayout.showToolTipForView(toolTip, mWeekView);
        mOrangeToolTipView.setOnToolTipViewClickedListener(this);
    }

    @Override
    public void onToolTipViewClicked(final ToolTipView toolTipView) {
        if (mOrangeToolTipView == toolTipView) {
            mOrangeToolTipView = null;
        }
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

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private Calendar minCalendars(Calendar cal1, Calendar cal2) {
        if (cal1.before(cal2)) {
            return cal1;
        }
        return cal2;
    }

    private Calendar maxCalendars(Calendar cal1, Calendar cal2) {
        if (cal1.after(cal2)) {
            return cal1;
        }
        return cal2;
    }

    private boolean checkDuplicate(Calendar cal1, Calendar cal2) {
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String init) {/* Init */};

}
