package com.example.alumnihub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.JobPost;
import com.example.alumnihub.data_models.User;
import com.example.alumnihub.utils.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {
    Context context;
    List<JobPost> jobPostList;
    UserServicesDB userServicesDB;

    public JobPostAdapter(Context context, List<JobPost> jobPostList) {
        this.context = context;
        this.jobPostList = jobPostList;
        this.userServicesDB = new UserServicesDB();
    }

    @NonNull
    @Override
    public JobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobPostViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_job_posts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobPostViewHolder holder, int position) {
        JobPost jobPost = jobPostList.get(position);
        holder.jobPostTitle.setText(TextUtils.capitalizeFirstLetter(jobPost.getJob_title()));
        holder.jobPostLocation.setText("Location : "+TextUtils.capitalizeFirstLetter(jobPost.getJob_location()));
        holder.jobPostDescription.setText(TextUtils.capitalizeFirstLetter(jobPost.getDescription()));
        holder.jobPostDomain.setText("Domain : "+TextUtils.capitalizeFirstLetter(jobPost.getDomain()));
        holder.jobPostRequiredSkill.setText("Required Skills : "+TextUtils.capitalizeFirstLetter(jobPost.getReq_skills()));
        holder.jobPostSalary.setText("Salary : "+TextUtils.capitalizeFirstLetter(jobPost.getSalary()));
        holder.jobPostMode.setText("Job Mode : "+TextUtils.capitalizeFirstLetter(jobPost.getWork_mode()));
        holder.jobPostContactEmail.setText("Contact Email : "+TextUtils.capitalizeFirstLetter(jobPost.getContact_email()));
        holder.jobPostContactPhoneNo.setText("Contact Phone no. "+TextUtils.capitalizeFirstLetter(jobPost.getContact_num()));

        userServicesDB.getUser(jobPost.getUser_id()).addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if(task.isSuccessful()){
                    User user = task.getResult();
                    if(user != null){
                        holder.jobPostUsername.setText("Posted by "+user.getUserName());
                    }else {
                        holder.jobPostUsername.setText("Posted by Anon user");
                    }
                }else {
                    holder.jobPostUsername.setText("Posted by Anon user");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobPostList.size();
    }

    public class JobPostViewHolder extends RecyclerView.ViewHolder{
        public TextView jobPostTitle,jobPostLocation,jobPostDescription,jobPostDomain,jobPostRequiredSkill,jobPostSalary,jobPostMode,
        jobPostContactEmail,jobPostContactPhoneNo,jobPostUsername;
        public JobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            jobPostTitle = itemView.findViewById(R.id.job_post_title);
            jobPostLocation = itemView.findViewById(R.id.job_post_location);
            jobPostDescription = itemView.findViewById(R.id.job_post_description);
            jobPostDomain = itemView.findViewById(R.id.job_post_domain);
            jobPostRequiredSkill = itemView.findViewById(R.id.job_post_required_skill);
            jobPostSalary = itemView.findViewById(R.id.job_post_salary);
            jobPostMode = itemView.findViewById(R.id.job_post_mode);
            jobPostContactEmail = itemView.findViewById(R.id.job_post_contact_email);
            jobPostContactPhoneNo = itemView.findViewById(R.id.job_post_contact_phone_no);
            jobPostUsername = itemView.findViewById(R.id.job_post_username);

        }
    }
}
