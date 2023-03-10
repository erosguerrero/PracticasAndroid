package es.ucm.fdi.googlebooksclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.BookViewHolder> {
    private ArrayList<BookInfo> mBooksData = new ArrayList<BookInfo>();

    private void setBooksData(List<BookInfo> data){
        mBooksData = (ArrayList<BookInfo>) data;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Crea la asociación entre el xml de datos (Layout principal) y el código.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_element, null, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        //Comunicación entre esta clase y BookViewHolder (La interna de abajo)
        holder.asignarDatos(mBooksData.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooksData.size();
    }

    public void updateBooksResultList(List<BookInfo> booksInfo){
        setBooksData(booksInfo);
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.book_text);
        }

        public void asignarDatos(BookInfo bf){
            item.setText(bf.getTitle());
        }

    }


}
