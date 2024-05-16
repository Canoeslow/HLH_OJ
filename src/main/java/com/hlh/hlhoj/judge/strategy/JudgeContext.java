package com.hlh.hlhoj.judge.strategy;

import com.hlh.hlhoj.model.dto.question.JudgeCase;
import com.hlh.hlhoj.judge.codesandbox.model.JudgeInfo;
import com.hlh.hlhoj.model.entity.Question;
import com.hlh.hlhoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}
