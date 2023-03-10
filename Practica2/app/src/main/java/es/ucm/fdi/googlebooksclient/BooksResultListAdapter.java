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
    TextView loadingText;

    public void setLoadingTextView(TextView t)
    {
        loadingText = t;
    }

    public void setBooksData(List<BookInfo> data){
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

        if(booksInfo.size() == 0)
            loadingText.setText("No se encontraron resultados"); //TODO poner una var para traduccion
        else
        loadingText.setText("");

        notifyDataSetChanged();
    }

    public void restart()
    {
        setBooksData(new ArrayList<BookInfo>());
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textAut;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.book_title);
            textAut = itemView.findViewById(R.id.book_aut);
        }

        public void asignarDatos(BookInfo bf){

            textTitle.setText(bf.getTitle());

            if(bf.getAuthorsList() != null)
            {
                if (bf.getAuthorsList().size() == 0)
                    textAut.setText("Autor anonimo");//TODO usar variable de string para que se traduzca solo
                else
                {
                    List<String> auxListAut = bf.getAuthorsList();
                    String listAutStr = "";
                    //metemos autores intermedios separados por comas
                    for(int i = 0; i < auxListAut.size()-1; i++)
                    {
                        listAutStr += auxListAut.get(i) + ", ";
                    }

                    //metemos ultimo autor (o si solo habia uno) sin coma despues
                    listAutStr += auxListAut.get(auxListAut.size()-1);

                    textAut.setText(listAutStr);
                }

            }

        }

    }


}
