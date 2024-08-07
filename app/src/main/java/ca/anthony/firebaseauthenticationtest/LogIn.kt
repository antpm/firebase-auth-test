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
import kotlin.math.sign


class LogIn : Fragment() {

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var login: Button
    private lateinit var signUp: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.LogInEmail)
        pass = view.findViewById(R.id.LogInPass)
        login = view.findViewById(R.id.LogInButton)
        signUp = view.findViewById(R.id.SignUpButton)
        auth = Firebase.auth

        //Navigate to signup fragment when button pressed
        signUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_logIn_to_signUp)
        }

        //when login is pressed, use auth to make sign in attempt
        login.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener{task->
                    //check if the sign in task was succesful, and navigate to welcome screen if so
                    if (task.isSuccessful){
                        Toast.makeText(requireActivity(), "Login Succesful", Toast.LENGTH_SHORT).show()
                        //the navigation action in the navgraph uses the popUpTo attribute to prevent the user from getting back to this screen by pressing the back button on their devices
                        Navigation.findNavController(view).navigate(R.id.action_logIn_to_welcome)
                    } else {
                        //if task was not succesful, display error message
                        Toast.makeText(requireActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        //check if the user is already logged in and navigate to welcome screen if so
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //the navigation action in the navgraph uses the popUpTo attribute to prevent the user from getting back to this screen by pressing the back button on their devices
            Navigation.findNavController(view).navigate(R.id.action_logIn_to_welcome)
        }
    }

}