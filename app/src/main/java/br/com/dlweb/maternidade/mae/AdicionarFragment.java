package br.com.dlweb.maternidade.mae;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.dlweb.maternidade.R;

public class AdicionarFragment extends Fragment {

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
    private FirebaseFirestore db;

    public AdicionarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.mae_fragment_adicionar, container, false);

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
        Button btnSalvar = v.findViewById(R.id.buttonAdicionar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;



    }
    private void adicionar () {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etCep.getText().toString().equals("")) {
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
        }    else {
            Mae m = new Mae();
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

            CollectionReference collectionMae = db.collection("Maes");
            collectionMae.add(m).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getActivity(), "Mãe salva!", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMae, new ListarFragment()).commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getActivity(), "Erro ao salvar a mãe!", Toast.LENGTH_LONG).show();
                    Log.d("AdicionarMae", "mensagem de erro: ", e);
                }
            });
        }
    }

}