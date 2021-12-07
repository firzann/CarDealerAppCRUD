package com.example.cardealer

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.List

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Users>)
    : ArrayAdapter<Users>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textMerk = view.findViewById<TextView>(R.id.editMerk)
        val textTipe = view.findViewById<TextView>(R.id.editTipe)
        val textWarna = view.findViewById<TextView>(R.id.editWarna)
        val textKapasitas = view.findViewById<TextView>(R.id.editKapasitasMesin)
        val textKondisi = view.findViewById<TextView>(R.id.editKondisi)
        val textHarga = view.findViewById<TextView>(R.id.editHarga)

        val btnUpdate = view.findViewById<Button>(R.id.updateBtn)
        val btnDelete = view.findViewById<Button>(R.id.deleteBtn)

        val user = list[position]

        textMerk.text = user.merk
        textTipe.text = user.tipe
        textWarna.text = user.warna
        textKapasitas.text = user.kapasitas
        textKondisi.text = user.kondisi
        textHarga.text = user.harga

        btnUpdate.setOnClickListener {
            showUpdateDialog(user)
        }
        btnDelete.setOnClickListener {
            Deleteinfo(user)
        }
        return view
    }

    private fun Deleteinfo(user: Users) {

        val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting…")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("Data_Mobil")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ActivityList::class.java)
        context.startActivity(intent)
    }

    private fun showUpdateDialog(user: Users) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textMerk = view.findViewById<EditText>(R.id.editMerk)
        val textTipe = view.findViewById<EditText>(R.id.editTipe)
        val textWarna = view.findViewById<EditText>(R.id.editWarna)
        val textKapasitas = view.findViewById<EditText>(R.id.editKapasitasMesin)
        val textKondisi = view.findViewById<EditText>(R.id.editKondisi)
        val textHarga = view.findViewById<EditText>(R.id.editHarga)

        textMerk.setText(user.merk)
        textTipe.setText(user.tipe)
        textWarna.setText(user.warna)
        textKapasitas.setText(user.kapasitas)
        textKondisi.setText(user.kondisi)
        textHarga.setText(user.harga)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("Data_Mobil")

            val merk = textMerk.text.toString().trim()

            val tipe = textTipe.text.toString().trim()

            val warna = textWarna.text.toString().trim()

            val kapasitas = textKapasitas.text.toString().trim()

            val kondisi = textKondisi.text.toString().trim()

            val harga = textHarga.text.toString().trim()

            if (merk.isEmpty()){
                textMerk.error = "please enter title"
                textMerk.requestFocus()
                return@setPositiveButton
            }

            if (tipe.isEmpty()){
                textTipe.error = "please enter date"
                textTipe.requestFocus()
                return@setPositiveButton
            }

            if (warna.isEmpty()){
                textWarna.error = "please enter description"
                textWarna.requestFocus()
                return@setPositiveButton
            }

            if (kapasitas.isEmpty()){
                textKapasitas.error = "please enter description"
                textKapasitas.requestFocus()
                return@setPositiveButton
            }

            if (kondisi.isEmpty()){
                textKondisi.error = "please enter description"
                textKondisi.requestFocus()
                return@setPositiveButton
            }

            if (harga.isEmpty()){
                textHarga.error = "please enter description"
                textHarga.requestFocus()
                return@setPositiveButton
            }

            val user = Users(user.id,merk, tipe, warna, kapasitas, kondisi, harga)

            dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }

}