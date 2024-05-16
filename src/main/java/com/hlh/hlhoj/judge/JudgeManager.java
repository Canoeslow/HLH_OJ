package com.hlh.hlhoj.judge;

import com.hlh.hlhoj.judge.strategy.DefaultJudgeStrategy;
import com.hlh.hlhoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.hlh.hlhoj.judge.strategy.JudgeContext;
import com.hlh.hlhoj.judge.strategy.JudgeStrategy;
import com.hlh.hlhoj.judge.codesandbox.model.JudgeInfo;
import com.hlh.hlhoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
