package fr.isen.francoisyatta.projectv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import fr.isen.francoisyatta.projectv2.databinding.ActivityConnexionBinding

class ConnexionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnexionBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnexionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.create.setOnClickListener {
            val intent = Intent(this, CreationActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()


                // Créer un nouveau compte avec l'adresse email et le mot de passe saisis
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Le compte a été créé avec succès
                            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                            // Rediriger l'utilisateur vers l'écran de connexion
                            val intent = Intent(this, ConnexionActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Il y a eu une erreur lors de la création du compte
                            Toast.makeText(this, "Account creation failed: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
    }

}


