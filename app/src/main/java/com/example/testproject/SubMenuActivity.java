package com.example.testproject;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubMenuActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.expandableListView);

        // 데이터 준비
        prepareListData();

        // 어댑터 설정
        expandableListAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

        // 그룹 클릭 리스너
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // 그룹 클릭 시 이벤트 처리 (예: 토스트 메시지)
                Toast.makeText(getApplicationContext(),
                        "Group Clicked " + listDataHeader.get(groupPosition),
                        Toast.LENGTH_SHORT).show();

                ImageView groupIndicator = v.findViewById(R.id.openMenu);
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    groupIndicator.setRotation(0);
                } else {
                    groupIndicator.setRotation(90);
                }
                return false;
            }
        });

        // 그룹 확장 리스너
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                // Apply animation only to child items when the group is expanded
                for (int i = 0; i < expandableListView.getExpandableListAdapter().getChildrenCount(groupPosition); i++) {
                    View childView = expandableListView.getChildAt(i);
                    if (childView != null) {
                        childView.startAnimation(AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.slide_down));
                    }
                }
            }
        });

        // 그룹 축소 리스너
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                // Optionally, apply animation when the group is collapsed
                for (int i = 0; i < expandableListView.getExpandableListAdapter().getChildrenCount(groupPosition); i++) {
                    View childView = expandableListView.getChildAt(i);
                    if (childView != null) {
                        childView.startAnimation(AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.slide_down));
                    }
                }
            }
        });

        // 차일드 클릭 리스너
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // 차일드 클릭 시 이벤트 처리 (예: 토스트 메시지)
                Toast.makeText(getApplicationContext(),
                        "Child Clicked " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // 메인 메뉴 추가
        listDataHeader.add("국어");
        listDataHeader.add("영어");
        listDataHeader.add("역사");

        // 서브 메뉴 추가
        List<String> LangMenu = new ArrayList<>();
        LangMenu.add("서브메뉴1");
        LangMenu.add("서브메뉴2");
        LangMenu.add("서브메뉴3");

        List<String> EngMenu = new ArrayList<>();
        EngMenu.add("서브메뉴1");
        EngMenu.add("서브메뉴2");
        EngMenu.add("서브메뉴3");

        List<String> HisMenu = new ArrayList<>();
        HisMenu.add("서브메뉴1");
        HisMenu.add("서브메뉴2");
        HisMenu.add("서브메뉴3");

        listDataChild.put(listDataHeader.get(0), LangMenu);
        listDataChild.put(listDataHeader.get(1), EngMenu);
        listDataChild.put(listDataHeader.get(2), HisMenu);
    }
}
