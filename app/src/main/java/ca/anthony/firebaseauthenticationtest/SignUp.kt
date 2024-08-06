package ca.anthony.firebaseauthenticationtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUp : Fragment() {

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var text: EditText
    private lateinit var create: Button

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.SignUpEmailText)
        pass = view.findViewById(R.id.SignUpPass)
        text= view.findViewById(R.id.SignUpSomeText)
        create = view.findViewById(R.id.SignUpCreate)

        auth = Firebase.auth

        create.setOnClickListener {
            createAccount(view)
        }
    }

    private fun createAccount(view: View){
        val userdata = hashMapOf(
            "text" to text.text.toString()
        )
        auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toast.makeText(requireActivity(), "Account Created", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    db.collection("users").document(user!!.uid).set(userdata)
                        .addOnSuccessListener {
                            auth.signOut()
                            Navigation.findNavController(view).popBackStack()
                        }

                }
            }
    }
}