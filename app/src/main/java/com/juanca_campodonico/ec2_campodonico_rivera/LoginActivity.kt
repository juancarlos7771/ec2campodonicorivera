package com.juanca_campodonico.ec2_campodonico_rivera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.juanca_campodonico.ec2_campodonico_rivera.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tilemail.editText?.addTextChangedListener { text ->
            binding.btnlogin.isEnabled = validateEmailPass(text.toString(), binding.tilpass.editText?.text.toString())
        }

        binding.tilpass.editText?.addTextChangedListener { text ->
            binding.btnlogin.isEnabled = validateEmailPass(binding.tilemail.editText?.text.toString(), text.toString())
        }

        binding.btnlogin.setOnClickListener {
            val email = binding.tilemail.editText?.text.toString()
            val password = binding.tilpass.editText?.text.toString()

            if (email == "ejemplo@idat.edu.pe" && password == "123456") {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("nombre", "Nene")
                startActivity(intent)
                Toast.makeText(this, "Bienvenido a la aplicación Los Michis Store", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error de inicio de sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateEmailPass(email: String, pass: String): Boolean {
        val isValidEmail = email == "ejemplo@idat.edu.pe"
        val isValidPass = pass == "123456"
        return isValidEmail && isValidPass
    }
}