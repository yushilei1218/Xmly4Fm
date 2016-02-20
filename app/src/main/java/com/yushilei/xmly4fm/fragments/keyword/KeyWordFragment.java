package com.yushilei.xmly4fm.fragments.keyword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.AutoSuggestAdapter;
import com.yushilei.xmly4fm.entities.AutoSuggestEntity;
import com.yushilei.xmly4fm.entities.SuggestAlbumEntity;
import com.yushilei.xmly4fm.entities.SuggestQueryEntity;
import com.yushilei.xmly4fm.fragments.AlbumDetailFragment;
import com.yushilei.xmly4fm.fragments.MainFragment;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class KeyWordFragment extends Fragment implements HotKwAndHistoryKwFragment.KeyWordSelectedListener {


    private TextView cancelOrSearchTv;
    private ImageView textClearImg;
    private EditText textInputEt;
    private ListView autoSuggestLv;
    private AutoSuggestAdapter adapter;

    private static final String HOT_AND_HISTORY = "hot";
    private static final String KW_SEARCH = "kw";

    private boolean needSuggest = true;

    public KeyWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_key_word, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelOrSearchTv = ((TextView) view.findViewById(R.id.key_word_cancel_or_search));
        textClearImg = ((ImageView) view.findViewById(R.id.key_word_clear_text));
        textInputEt = ((EditText) view.findViewById(R.id.key_word_edit_text));
        autoSuggestLv = ((ListView) view.findViewById(R.id.key_word_list_view));

        cancelOrSearchTv.setText("取消");
        adapter = new AutoSuggestAdapter(getContext());
        autoSuggestLv.setAdapter(adapter);
        //给ListView 设置监听
        autoSuggestLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter().getItem(position) instanceof SuggestQueryEntity) {
                    SuggestQueryEntity item = (SuggestQueryEntity) parent.getAdapter().getItem(position);
                    showKwSearchFragment(item.getKeyword());
                }
                if (parent.getAdapter().getItem(position) instanceof SuggestAlbumEntity) {
                    SuggestAlbumEntity item = (SuggestAlbumEntity) parent.getAdapter().getItem(position);
                    FragmentManager manager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    KeyWordFragment keyWordFragment = ((KeyWordFragment) manager.findFragmentByTag("KeyWordFragment"));
                    transaction.setCustomAnimations(R.anim.open_in, R.anim.open_out, R.anim.colse_in, R.anim.colse_out);
                    transaction.add(R.id.main_container, AlbumDetailFragment.newInstance(item.getId()));
                    if (keyWordFragment != null) {
                        transaction.hide(keyWordFragment);
                    }
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        //实现 文本清空功能
        textClearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputEt.setText(null);
            }
        });
        //处理 右上角 取消 or 搜索 功能
        cancelOrSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = cancelOrSearchTv.getText();
                if (text.equals("取消")) {
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().popBackStack();
                } else {
                    String kw = textInputEt.getText().toString();
                    HotKwAndHistoryKwFragment.writeKwToSharedPreferences(getContext(), kw);
                    showKwSearchFragment(kw);
                }
            }
        });
        //实现输入框监听功能
        textInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (needSuggest && !TextUtils.isEmpty(s)) {
                    textClearImg.setVisibility(View.VISIBLE);
                    cancelOrSearchTv.setText("搜索");
                    showAutoListView();
                    NetWorkUtils.getSuggestService().getAutoSuggestEntity(s.toString()).enqueue(new Callback<AutoSuggestEntity>() {
                        @Override
                        public void onResponse(Response<AutoSuggestEntity> response, Retrofit retrofit) {
                            adapter.clearAll();
                            adapter.addAll(response.body().getAlbumResultList(), s.toString());
                            adapter.addAll(response.body().getQueryResultList(), s.toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } else {
                    cancelOrSearchTv.setText("取消");
                    textClearImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //默认进入展示 历史记录 与热门搜索
        showHotKwAndHistoryFragment();
    }

    private void showHotKwAndHistoryFragment() {
        autoSuggestLv.setVisibility(View.GONE);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HotKwAndHistoryKwFragment fragment = new HotKwAndHistoryKwFragment();
        fragment.setOnKeyWordSelectedListener(this);
        transaction.replace(R.id.key_word_container, fragment, HOT_AND_HISTORY);
        transaction.commit();
    }

    private void showKwSearchFragment(String kw) {
        Log.d("KeyWordFragment", "showKwSearchFragment");

        needSuggest = false;
        textInputEt.setText(kw);
        autoSuggestLv.setVisibility(View.GONE);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.key_word_container, KwSearchFragment.newInstance(kw), KW_SEARCH);
        transaction.commit();
        needSuggest = true;
    }

    private void showAutoListView() {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment kwSearchFragment = manager.findFragmentByTag(KW_SEARCH);
        Fragment hotKwAndHistoryKwFragment = manager.findFragmentByTag(HOT_AND_HISTORY);
        if (kwSearchFragment != null) {
            transaction.detach(kwSearchFragment);
        }
        if (hotKwAndHistoryKwFragment != null) {
            transaction.detach(hotKwAndHistoryKwFragment);
        }
        transaction.commit();
        autoSuggestLv.setVisibility(View.VISIBLE);
    }

    /**
     * 用来监听 HotKwAndHistoryKwFragment 选中关键词触发的回调
     *
     * @param kw HotKwAndHistoryKwFragment传回的关键词
     */
    @Override
    public void onKeyWordSelected(String kw) {
        showKwSearchFragment(kw);
    }
}
