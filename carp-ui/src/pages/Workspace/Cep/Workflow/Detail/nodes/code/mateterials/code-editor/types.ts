interface CodeValue {
  language: string;
  content?: string;
}

interface CodeEditorProps {
  codeValue: CodeValue;
  onChange: (value?: CodeValue) => void;
  readonly?: boolean;
}

enum LanguageType {
  JavaScript = 'javascript',
  Groovy = 'groovy',
  Python = 'python',
}

const defaultJavaScriptCode = `// Here, you can retrieve input variables from the node using 'params' and output results using 'ret'.
// 'params' has been correctly injected into the environment.
// Here's an example of getting the value of the parameter named 'input' from the node input:
// const input = params.input;
// Here's an example of outputting a 'ret' object containing multiple data types:
// const ret = { "name": 'Xiaoming', "hobbies": ["Reading", "Traveling"] };

async function main({ params }) {
  // Build the output object
  const ret = {
    key0: params.input + params.input, // Concatenate the input parameter 'input' twice
    key1: ["hello", "world"], // Output an array
    key2: { // Output an Object
      key21: "hi"
    },
  };

  return ret;
}`;

const defaultGroovyCode = `
// Here, you can retrieve input variables from the node using 'params' and output results using 'ret'.
// 'params' has been correctly injected into the environment.

ret = {
  "key0": params.input + params.input, // Concatenate the input parameter 'input' twice
  "key1": ["hello", "world"], // Output an array
  "key2": { // Output an Object
    "key21": "hi"
  },
};

return ret;
`;

const defaultPythonCode = `
// Here, you can retrieve input variables from the node using 'params' and output results using 'ret'.
// 'params' has been correctly injected into the environment.

`;

const OPTION_LIST = [
  {
    label: 'JavaScript',
    value: LanguageType.JavaScript,
  },
  {
    label: 'Groovy',
    value: LanguageType.Groovy,
  },
  {
    label: 'Python',
    value: LanguageType.Python,
  },
]

const LANGUATE_DEFAULT_CODE = {
  javascript: defaultJavaScriptCode,
  groovy: defaultGroovyCode,
  python: defaultPythonCode,
}

const DEFAULT_CODE_VALUE = {
  language: LanguageType.JavaScript,
  content: LANGUATE_DEFAULT_CODE[LanguageType.JavaScript],
}

export {CodeValue, CodeEditorProps, OPTION_LIST, LANGUATE_DEFAULT_CODE, DEFAULT_CODE_VALUE }
