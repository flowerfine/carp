import { useEffect, useState } from "react";
import { TypeScriptCodeEditor } from "@flowgram.ai/form-materials";
import { Select } from "@douyinfe/semi-ui";
import { CodeEditorProps, LANGUATE_DEFAULT_CODE, OPTION_LIST } from "./types";
import { FormItem } from "../../../../form-components";


export const CodeEditor = ({ codeValue, onChange, readonly }: CodeEditorProps) => {

  const [language, setLanguage] = useState<string | undefined>();
  const [code, setCode] = useState<string | undefined>();

  useEffect(() => {
    setLanguage(codeValue.language);
    setCode(codeValue.content);
  }, [codeValue]);

  const handleLaguageChange = (value: string) => {
    setLanguage(value);
    const defaultCode = LANGUATE_DEFAULT_CODE[value];
    console.log('handleLaguageChange', value, defaultCode)
    setCode(defaultCode)
    onChange({ language: value, content: defaultCode })
  }

  const getLanguage = () => {
    if (language) {
      if (language === "javascript") {
        return "typescript";
      } else if (language === "groovy") {
        return "python";
      } else {
        return language;
      }
    }
    return "javascript";
  }

  return (
    <>
      <FormItem name={'language'} type={'string'} required>
        <Select
          value={language}
          onChange={(value) => handleLaguageChange(value)}
          style={{ width: 85, maxWidth: 85, minWidth: 85 }}
          size="small"
          disabled={readonly}
          optionList={OPTION_LIST}
        />
      </FormItem>

      <FormItem name="code" type="string" vertical required>
        <TypeScriptCodeEditor
          value={code}
          languageId={getLanguage()}
          onChange={(value) => onChange({ language, content: value })}
          readonly={readonly}
        />
      </FormItem>
    </>
  )

}
