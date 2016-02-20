package com.yushilei.xmly4fm.fragments.keyword;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.entities.HotKeyWordEntity;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotKwAndHistoryKwFragment extends Fragment implements Callback<HotKeyWordEntity> {


    private GridView hotKwGd;
    private GridView historyKwGd;
    private LinearLayout historyContainer;
    private TextView clearHistoryTv;
    private Call<HotKeyWordEntity> call;

    public HotKwAndHistoryKwFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        call = NetWorkUtils.getService().getHotKeyWordEntity();
        call.enqueue(this);
    }

    @Override
    public void onDestroy() {
        call.cancel();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_kw_and_history_kw, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyContainer = (LinearLayout) view.findViewById(R.id.hot_kw_and_history_container);
        clearHistoryTv = (TextView) view.findViewById(R.id.hot_kw_and_history_kw_clear_history);
        historyKwGd = (GridView) view.findViewById(R.id.hot_kw_and_history_kw_history);
        hotKwGd = (GridView) view.findViewById(R.id.hot_kw_and_history_kw_hotkw);

        showHistoryKwUI();
    }

    private void showHistoryKwUI() {
        SharedPreferences historyKw = getContext().getSharedPreferences("historyKw", Context.MODE_PRIVATE);
        String historyKwString = historyKw.getString("historyKw", null);
        if (historyKwString == null) {
            historyContainer.setVisibility(View.GONE);
        } else {
            historyContainer.setVisibility(View.VISIBLE);
            clearHistoryTv.setVisibility(View.VISIBLE);
            String[] split = historyKwString.split(",");
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(split));
            historyKwGd.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.kwy_word_item, R.id.key_word_text, list));

            clearHistoryTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences historyKw = getContext().getSharedPreferences("historyKw", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = historyKw.edit();
                    edit.clear();
                    edit.apply();
                    historyContainer.setVisibility(View.GONE);
                }
            });
            historyKwGd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String kw = (String) parent.getAdapter().getItem(position);
                    writeKwToSharedPreferences(getContext(), kw);
                    if (listener != null) {
                        listener.onKeyWordSelected(kw);
                    }
                }
            });
        }
    }

    /**
     * 给定一个String 插入 指定SharedPreferences 并保持 String被分割的长度小于等于6
     *
     * @param context
     * @param kw
     */
    public static void writeKwToSharedPreferences(Context context, String kw) {
        if (!TextUtils.isEmpty(kw)) {
            SharedPreferences historyKw = context.getSharedPreferences("historyKw", Context.MODE_PRIVATE);
            String historyKwString = historyKw.getString("historyKw", null);
            SharedPreferences.Editor edit = historyKw.edit();
            if (historyKwString != null) {
                String[] split = historyKwString.split(",");
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(split));
                if (list.contains(kw)) {
                    list.remove(kw);
                }
                list.add(0, kw);
                List<String> subList = list.subList(0, Math.min(list.size(), 6));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < subList.size(); i++) {
                    String s = subList.get(i) + ",";
                    sb.append(s);
                }
                sb.deleteCharAt(sb.length() - 1);
                edit.putString("historyKw", sb.toString());
            } else {
                edit.putString("historyKw", kw);
            }
            edit.apply();
        }
    }

    @Override
    public void onResponse(Response<HotKeyWordEntity> response, Retrofit retrofit) {
        if (response != null && response.body().getList() != null && response.body().getList().size() > 0) {
            hotKwGd.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.kwy_word_item, R.id.key_word_text, response.body().getList()));
            hotKwGd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String kw = (String) parent.getAdapter().getItem(position);
                    writeKwToSharedPreferences(getContext(), kw);
                    if (listener != null) {
                        listener.onKeyWordSelected(kw);
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }

    private KeyWordSelectedListener listener;

    public void setOnKeyWordSelectedListener(KeyWordSelectedListener listener) {
        this.listener = listener;
    }

    public interface KeyWordSelectedListener {
        void onKeyWordSelected(String kw);
    }
}
