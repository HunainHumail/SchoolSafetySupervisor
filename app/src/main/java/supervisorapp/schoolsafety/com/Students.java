package supervisorapp.schoolsafety.com;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;
import java.util.List;

public class Students extends Fragment {
    private StudentsAdapter adapter;
    private List<StudentItem> studentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.students, container, false);
        setHasOptionsMenu(true);
        fillExampleList();
        //setUpRecyclerView();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new StudentsAdapter(studentList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        return view;
    }

//    private void setUpRecyclerView() {
//        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        adapter = new StudentsAdapter(studentList);
//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }


    private void fillExampleList() {
        studentList = new ArrayList<>();
        studentList.add(new StudentItem(R.drawable.userimg, "Abdullah", "Phone Number"));
        studentList.add(new StudentItem(R.drawable.userimg, "Two", "Eleven"));
        studentList.add(new StudentItem(R.drawable.userimg, "Three", "Twelve"));
        studentList.add(new StudentItem(R.drawable.userimg, "Four", "Thirteen"));
        studentList.add(new StudentItem(R.drawable.userimg, "Five", "Fourteen"));
        studentList.add(new StudentItem(R.drawable.userimg, "Six", "Fifteen"));
        studentList.add(new StudentItem(R.drawable.userimg, "Seven", "Sixteen"));
        studentList.add(new StudentItem(R.drawable.userimg, "Eight", "Seventeen"));
        studentList.add(new StudentItem(R.drawable.userimg, "Nine", "Eighteen"));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.student_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}

