package ru.job4j.dreamjob;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CandidateStore;
import ru.job4j.dreamjob.store.PostStore;
import ru.job4j.dreamjob.store.PsqlCandidateStore;
import ru.job4j.dreamjob.store.PsqlPostStore;

public class PsqlMain {

    public static void main(String[] args) {
        PostStore postStore = PsqlPostStore.getInstance();
        Post p = new Post(0, "Вакансия");
        postStore.save(p);
        System.out.println("Добавленные вакансии:");
        for (Post post : postStore.findAll()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        p.setDescription("Описание вакансии");
        postStore.save(p);
        p = postStore.getById(1);
        System.out.println("Измененная вакансия: " + p);
        postStore.delete(1);
        System.out.println("Список вакансий после удаления:");
        for (Post post : postStore.findAll()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        Candidate c = new Candidate(0, "Candidate", "Position");
        CandidateStore candidateStore = PsqlCandidateStore.getInstance();
        candidateStore.save(c);
        System.out.println("Добавленные кандидаты:");
        for (Candidate candidate : candidateStore.findAll()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        c.setPosition("Java разработчик");
        candidateStore.save(c);
        c = candidateStore.getById(1);
        System.out.println("Измененный кандидат: " + c);
        candidateStore.delete(1);
        System.out.println("Список кандидатов после удаления:");
        for (Candidate candidate : candidateStore.findAll()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
    }
}
