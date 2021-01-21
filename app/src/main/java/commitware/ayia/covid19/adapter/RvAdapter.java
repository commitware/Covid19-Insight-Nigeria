package commitware.ayia.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.covid19.models.Cases;
import commitware.ayia.covid19.models.CasesList;
import commitware.ayia.covid19.R;


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> implements Filterable {

  private List<Cases> mValues;
  private List<Cases> mValuesFilteredList;
    private Context mContext;


    public RvAdapter(Context context) {


        mContext = context;

    }

    public void setmValues(List<Cases>values){

        mValues = values;

        mValuesFilteredList = values;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public TextView textView2;
        Cases item;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.tvOne);
            textView2 =  v.findViewById(R.id.tvTwo);

        }

        public void setData(Cases item) {
            this.item = item;
            textView2.setVisibility(View.VISIBLE);
            textView.setText(item.getCases());
            textView2.setText("location");

        }
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValuesFilteredList.get(position));

    }

    @Override
    public int getItemCount() {

        return mValuesFilteredList.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                   mValuesFilteredList = mValues;
                } else {
                    List<Cases> filteredList = new ArrayList<>();
                    for (Cases row : mValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCritical().toLowerCase().contains(charString.toLowerCase().trim())) {
                            filteredList.add(row);
                        }
                    }

                    mValuesFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mValuesFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mValuesFilteredList = (List<Cases>) filterResults.values;
                //mValuesFilteredList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public List<Cases> getmValuesFilteredList() {
        return mValuesFilteredList;
    }
}