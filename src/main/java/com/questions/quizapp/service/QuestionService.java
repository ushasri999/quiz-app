package com.questions.quizapp.service;

import com.questions.quizapp.model.Question;
import com.questions.quizapp.repositories.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public List<Question> getAllQuestions() {

        return questionDao.findAll();
    }

    public ResponseEntity<List<Question>> getAllQuestionsRE() {
        try
        {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
    
    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategoryRE(String category) {
        try
        {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    public String addQuestion(Question question) {
        questionDao.save(question);
        return "OK";
    }

    public ResponseEntity<String> addQuestionRE(Question question) {
        try
        {
            questionDao.save(question);
            return new ResponseEntity<>("add OK", HttpStatus.CREATED);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return  new ResponseEntity<>("add NOK", HttpStatus.BAD_REQUEST);
    }

    public String deleleteById(Integer id) {
        questionDao.deleteById(id);
        return "Delete OK";
    }

    @Transactional
    public String updateById(Integer id, Question question) {
        Optional<Question> existingQuestion = questionDao.findById(id);

        if (existingQuestion.isPresent()) {
            Question questionToUpdate = existingQuestion.get();

            // Update the fields of the existing question with the values from updatedQuestion
            questionToUpdate.setCategory(question.getCategory());
            questionToUpdate.setQuestionTitle(question.getQuestionTitle());
            questionToUpdate.setOption1(question.getOption1());
            questionToUpdate.setOption2(question.getOption2());
            questionToUpdate.setOption3(question.getOption3());
            questionToUpdate.setOption4(question.getOption4());
            questionToUpdate.setRightAnswer(question.getRightAnswer());
            questionToUpdate.setDifficultylevel(question.getDifficultylevel());

            questionDao.save(questionToUpdate);
            return "Update OK";
        } else {
            return "Question not found"; 
        }
    }    

}
