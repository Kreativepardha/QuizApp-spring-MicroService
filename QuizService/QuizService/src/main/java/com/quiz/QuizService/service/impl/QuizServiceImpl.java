package com.quiz.QuizService.service.impl;

import com.quiz.QuizService.entity.Quiz;
import com.quiz.QuizService.repository.QuizRepository;
import com.quiz.QuizService.service.QuestionClient;
import com.quiz.QuizService.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private QuizRepository quizRepository;

    private QuestionClient questionClient;

    public QuizServiceImpl(QuizRepository quizRepository,QuestionClient questionClient) {
        this.questionClient = questionClient;
        this.quizRepository = quizRepository;
    }


    @Override
    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> get() {
         List<Quiz> quizzes = quizRepository.findAll();
       List<Quiz> newQuizList =  quizzes.stream().map(quiz -> {
            quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
            return  quiz;
         }).collect(Collectors.toList());
        return newQuizList ;
    }

    @Override
    public Quiz get(Long id) {
    Quiz quiz =  quizRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id does not exists"));
        quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
    return quiz;
    }
}
