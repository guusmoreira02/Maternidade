package br.com.dlweb.maternidade.mae;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.dlweb.maternidade.R;

public class EditarFragment extends Fragment {

    private EditText etNome;
    private EditText etCep;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etBairro;
    private EditText etCidade;
    private EditText etFixo;
    private EditText etCelular;
    private EditText etComercial;
    private EditText etDataNascimento;
    private Mae m;
    private FirebaseFirestore db;


    public EditarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.mae_fragment_editar, container, false);

        etNome = v.findViewById(R.id.editTextNome);
        etCep = v.findViewById(R.id.editTextCep);
        etLogradouro = v.findViewById(R.id.editTextLogradouro);
        etNumero = v.findViewById(R.id.editTextNumero);
        etBairro = v.findViewById(R.id.editTextBairro);
        etCidade = v.findViewById(R.id.editTextCidade);
        etFixo = v.findViewById(R.id.editTextFixo);
        etCelular = v.findViewById(R.id.editTextCelular);
        etComercial= v.findViewById(R.id.editTextComercial);
        etDataNascimento = v.findViewById(R.id.editTextDataNascimento);

        db = FirebaseFirestore.getInstance();
        Bundle b = getArguments();
        String id_mae = b != null ? b.getString("id") : null;

        assert id_mae != null;
        DocumentReference documentMae = db.collection("Maes").document(id_mae);
        documentMae.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete( Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        m = task.getResult().toObject(Mae.class);
                        assert m != null;
                        etNome.setText(m.getNome());
                        etCep.setText(m.getCep());
                        etLogradouro.setText(m.getLogradouro());
                        etNumero.setText(String.valueOf(m.getNumero()));
                        etBairro.setText(m.getBairro());
                        etCidade.setText(m.getCidade());
                        etFixo.setText(m.getFixo());
                        etCelular.setText(m.getCelular());
                        etComercial.setText(m.getComercial());
                        etDataNascimento.setText(m.getData_nascimento());
                    }else {
                        Toast.makeText(getActivity(), "Erro ao buscar a mãe!", Toast.LENGTH_LONG).show();
                        Log.d("EditarMae", "nenhum documento encontrado");
                    }
                } else {
                    Toast.makeText(getActivity(), "Erro ao buscar a mãe!", Toast.LENGTH_LONG).show();
                    Log.d("EditarMae", "erro: ", task.getException());
                }
            }
        });

        Button btnEditar = v.findViewById(R.id.buttonEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_mae);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_excluir_mae);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_mae);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
// Não faz nada
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        return v;
    }

    private void editar (String id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        }else if (etCep.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o cep!", Toast.LENGTH_LONG).show();
        }else if (etLogradouro.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o Logradouro!", Toast.LENGTH_LONG).show();
        }else if (etNumero.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o Numero!", Toast.LENGTH_LONG).show();
        } else if (etBairro.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o Bairro!", Toast.LENGTH_LONG).show();
        } else if (etCidade.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a Cidade!", Toast.LENGTH_LONG).show();
        } else if (etFixo.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o Telefone Fixo!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o Celular!", Toast.LENGTH_LONG).show();
        } else if (etComercial.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o numero Comercial!", Toast.LENGTH_LONG).show();
        }else if (etDataNascimento.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de nascimento!", Toast.LENGTH_LONG).show();
        }else {
            m.setNome(etNome.getText().toString());
            m.setCep(etCep.getText().toString());
            m.setLogradouro(etLogradouro.getText().toString());
            m.setNumero(Integer.parseInt(etNumero.getText().toString()));
            m.setBairro(etBairro.getText().toString());
            m.setCidade(etCidade.getText().toString());
            m.setFixo(etFixo.getText().toString());
            m.setCelular(etCelular.getText().toString());
            m.setComercial(etComercial.getText().toString());
            m.setData_nascimento(etDataNascimento.getText().toString());
            DocumentReference documentMae = db.collection("Maes").document(id);
            documentMae.set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Mãe atualizada!", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMae, new ListarFragment()).commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.w("Editar Mãe", "erro: ", e);
                        }
                    });
        }
    }

    private void excluir(String id) {
        db.collection("Maes").document(id)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Mãe excluída!", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMae, new ListarFragment()).commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("ExcluirMae", "erro: ", e);
                    }
                });
    }

}