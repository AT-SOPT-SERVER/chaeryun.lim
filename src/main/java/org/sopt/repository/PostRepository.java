package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.util.PostIdGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private final String filePath = "src/main/java/org/sopt/assets/Post.txt";
    List<Post> postList = new ArrayList<>();

    public PostRepository() {
        loadPostData();
    }

    public void save(final Post post){

        try {
            File file = new File(filePath);

            // 디렉토리 생성
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 파일 생성
            if (!file.exists()) {
                file.createNewFile();
            }

            // bufferedWriter 생성
            BufferedWriter br = new BufferedWriter(new FileWriter(file, true));

            br.write(String.valueOf(post.getId()));
            br.write(", " + post.getTitle());
            br.newLine();

            // 파일 저장
            postList.add(post);

            br.flush();
            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Post> findAll(){
        return postList;
    }

    public Post findByPostById(final int id){

        for(Post post : postList){

            if (post.getId() == id){
                return post;
            }
        }

        return null;
    }

    public boolean deleteById(final int id){
        for(Post post : postList){
            if (post.getId() == id){
                return postList.remove(post);
            }
        }

        return false;
    }

    // 파일 수정 로직
    public boolean updateById(final int id, final String title){

        return postList.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .map(post -> {
                    post.updateTitle(title);
                    saveFile();
                    return true;
                })
                .orElse(false);
    }

    /**
     * 내부 로직
     */
    private void loadPostData(){

        try {

            File file = new File(filePath);

            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(filePath));

                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(", ");

                    // ID 생성기 사용해서 index 맞추기
                    Post post = new Post(PostIdGenerator.generatePostId(), split[1]);
                    postList.add(post);
                }

                br.close();
            }
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    // 파일 저장하기
    private void saveFile(){
        try {
            File file = new File(filePath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false)); // 덮어쓰기 모드

            for (Post post : postList) {
                bw.write(post.getId() + ", " + post.getTitle());
                bw.newLine();
            }

            bw.flush();
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
