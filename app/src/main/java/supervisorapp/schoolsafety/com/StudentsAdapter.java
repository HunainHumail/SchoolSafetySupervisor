package supervisorapp.schoolsafety.com;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> implements Filterable {

    private List<StudentItem> studentList;
    private List<StudentItem> studentListFull;

    class StudentsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public StudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
        }
    }

    StudentsAdapter(List<StudentItem> studentList)
    {
        this.studentList = studentList;
        studentListFull = new ArrayList<>(studentList);
    }


    @NonNull
    @Override
    public StudentsAdapter.StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
        return new StudentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsAdapter.StudentsViewHolder holder, int position) {

        StudentItem currentItem = studentList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public Filter getFilter() {
        return studentFilter;
    }

    private Filter studentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudentItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length()==0)
            {
                filteredList.addAll(studentListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (StudentItem item : studentListFull) {
                    if (item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            studentList.clear();
            studentList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
