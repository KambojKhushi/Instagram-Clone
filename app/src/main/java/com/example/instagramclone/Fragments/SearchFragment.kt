package com.example.instagramclone.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Adapter.UserAdapter
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var recyclerView:RecyclerView?=null
    private var userAdapter:UserAdapter?=null
    private var mUser:MutableList<User>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView=view.findViewById(R.id.recycler_view_search)
        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager=LinearLayoutManager(context)

        mUser=ArrayList()
        userAdapter=context?.let { UserAdapter(it, mUser as ArrayList<User>,true) }
        recyclerView?.adapter=userAdapter

        view.search_edit_text.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               if(view.search_edit_text.text.toString()==""){

               }
                else{
                    recyclerView?.visibility=View.VISIBLE
                   retrieveUsers()
                   searchUser(p0.toString().toLowerCase())
               }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        return view
    }

    private fun searchUser(input: String) {
        val query=FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .orderByChild("fullname")
            .startAt(input).endAt(input + "\uf8ff")
        query.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mUser?.clear()
                for(snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        mUser?.add(user)
                    }
                }
                    userAdapter?.notifyDataSetChanged()
                }


            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun retrieveUsers() {
        val usersRef=FirebaseDatabase.getInstance().getReference().child("Users")
        usersRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(view?.search_edit_text?.text.toString()==""){
                    mUser?.clear()
                    for(snapshot in dataSnapshot.children){
                        val user=snapshot.getValue(User::class.java)
                        if(user!=null){
                            mUser?.add(user)
                        }
                    }
                    userAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}