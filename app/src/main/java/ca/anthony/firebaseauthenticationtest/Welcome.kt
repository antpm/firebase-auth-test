package ca.anthony.firebaseauthenticationtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class Welcome : Fragment() {

    private lateinit var text: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var logout: Button
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        text = view.findViewById(R.id.WelcomeText)
        logout = view.findViewById(R.id.WelcomeLogOut)

        //get the currently logged in user
        val user = auth.currentUser

        //get the data for the currently logged in user from the users collection
        db.collection("users").document(user!!.uid).get().addOnSuccessListener {doc ->
            text.text = doc.get("text").toString()
        }

        logout.setOnClickListener {
            //sign the user out and navigate back to the login screen
            //the navigation action in the navgraph uses the popUpTo attribute to prevent the user from getting back to this screen by pressing the back button on their devices
            auth.signOut()
            Navigation.findNavController(view).navigate(R.id.action_welcome_to_logIn)
            Toast.makeText(requireActivity(), "Goodbye", Toast.LENGTH_SHORT).show()
        }


    }
}