package com.yushilei.xmly4fm.fragments.category;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.xmly4fm.R;
import com.yushilei.xmly4fm.adapters.CommonFragmentPagerAdapter;
import com.yushilei.xmly4fm.entities.CategoryEntity;
import com.yushilei.xmly4fm.entities.CategoryListEntity;
import com.yushilei.xmly4fm.entities.FragmentType;
import com.yushilei.xmly4fm.entities.category.CategoryTagEntity;
import com.yushilei.xmly4fm.entities.category.CategoryTagListEntity;
import com.yushilei.xmly4fm.fragments.AlbumDetailFragment;
import com.yushilei.xmly4fm.fragments.keyword.KeyWordFragment;
import com.yushilei.xmly4fm.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends Fragment {

    private static final String CATEGORY_ID = "categoryId";
    private static final String CATEGORY_TYPE = "contentType";
    private static final String CATEGORY_TITLE = "title";
    private ViewPager pager;
    private TabLayout tabBar;

    public CategoryDetailFragment() {
    }

    public static CategoryDetailFragment newInstance(int categoryId, String contentType, String title) {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        //categoryId=3&contentType=album
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, categoryId);
        bundle.putString(CATEGORY_TYPE, contentType);
        bundle.putString(CATEGORY_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String title = bundle.getString(CATEGORY_TITLE);
        String contentType = bundle.getString(CATEGORY_TYPE);
        int categoryId = bundle.getInt(CATEGORY_ID);

        ImageView backImg = (ImageView) view.findViewById(R.id.category_detail_back);
        ImageView searchImg = (ImageView) view.findViewById(R.id.category_detail_search);
        TextView categoryTitle = (TextView) view.findViewById(R.id.category_detail_title);

        pager = (ViewPager) view.findViewById(R.id.category_detail_pager);
        tabBar = (TabLayout) view.findViewById(R.id.category_detail_tab_bar);

        categoryTitle.setText(title);
        //返回键
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().popBackStack();
            }
        });
        //搜索键
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = manager.findFragmentByTag("CategoryDetailFragment");
                transaction.setCustomAnimations(R.anim.open_in, R.anim.open_out, R.anim.colse_in, R.anim.colse_out);
                if (fragment != null) {
                    transaction.hide(fragment);
                }
                transaction.add(R.id.main_container, new KeyWordFragment(), "KeyWordFragment");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        //
        NetWorkUtils.getService().getCategoryTagListEntity(categoryId, contentType, "android").enqueue(new Callback<CategoryTagListEntity>() {
            @Override
            public void onResponse(Response<CategoryTagListEntity> response, Retrofit retrofit) {
                List<CategoryTagEntity> list = response.body().getList();
                List<FragmentType> data = new ArrayList<FragmentType>();
                for (int i = 0; i < list.size(); i++) {
                    FragmentType type = new FragmentType(list.get(i).getTname(), new CategoryRecommendFragment());
                    data.add(type);
                }
                pager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), data));
                tabBar.setupWithViewPager(pager);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
