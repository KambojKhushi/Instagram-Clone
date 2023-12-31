package com.example.instagramclone.Adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Adapter.PostAdapter.ViewHolder
import com.example.instagramclone.Model.Post
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_accounts_settings.*

class PostAdapter(private val mContext:Context,private val mPost:List<Post>) :RecyclerView.Adapter<ViewHolder>()
{
    private var firebaseUser:FirebaseUser?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(mContext).inflate(R.layout.posts_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPost.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser=FirebaseAuth.getInstance().currentUser
        val post=mPost[position]
        Picasso.get().load(post.getPostImage()).into(holder.postImage)

        publisherInfo(holder.profileImage, holder.userName, holder.publisher, post.getPublisher())
    }

    inner class ViewHolder(@NonNull itemView:View):RecyclerView.ViewHolder(itemView){
        var profileImage:CircleImageView
        var postImage:ImageView
        var likeButton:ImageView
        var commentButton:ImageView
        var saveButton:ImageView
        var userName:TextView
        var likes:TextView
        var publisher:TextView
        var description:TextView
        var comments:TextView

        init {
            profileImage=itemView.findViewById(R.id.user_profile_image_post)
            postImage=itemView.findViewById(R.id.post_image_home)
            likeButton=itemView.findViewById(R.id.post_image_like_btn)
            commentButton=itemView.findViewById(R.id.post_image_comment_btn)
            saveButton=itemView.findViewById(R.id.post_save_comment_btn)
            userName=itemView.findViewById(R.id.user_name_post)
            likes=itemView.findViewById(R.id.likes)
            publisher=itemView.findViewById(R.id.publisher)
            description=itemView.findViewById(R.id.description)
            comments=itemView.findViewById(R.id.comments)
        }
    }
    private fun publisherInfo(profileImage: CircleImageView, userName: TextView, publisher: TextView, publisherId: String)
    {
        val userRef=FirebaseDatabase.getInstance().reference.child("Users").child(publisherId)
        userRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user=snapshot.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.profile).into(profileImage)
                    userName.text=user!!.getUsername()
                    publisher.text=user!!.getfullname()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}