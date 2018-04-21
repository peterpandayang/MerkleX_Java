Introduction
------------

Hello and thank you for taking the time to work on this problem for the interview process of MerkleX. We hope that this problem can fairly evaluate your development skills and we are open to feedback at hr-feedback@merklex.io.

In this problem you are tasked with creating a JSON query tool.

You can use any of the following languages: JavaScript (NodeJS), Java7/8, C++11, or C.

You must provide source code, and if needed, a file called compile.sh to prepare the executable. The executable must be portable and be able to compile/run on modern POSIX systems.

You should also create a bash script called test.sh to verify the basic function of your tool.


Submission
---------

After completed, please provide a ZIP of the required materials. In an isolated environment we will run the following commands to verify the completeness of the program before reviewing bash commands.

```
unzip submission.zip
cd submission # directory name can be different. We'll just enter what seems to be the root directory for this project.
if [ -f "compile.sh" ]; then
  bash compile.sh;
fi
chmod +x ./json_tool
bash test.sh
```

Tool Details
----------------------------

Tool's Usage

./json_tool <json_file_path> <query_string>

json_tool should read the contents of <json_file_path> and print the contents found under <query_string> to STDOUT.

Examples:

```
1:

cat file.json
{ "a": "hello" }

./json_tool file.json a
"hello"

2:

cat file.json
{ "test": ["a", 1, "c"] }

./json_tool file.json test.2
"c"

3:

cat file.json
{ "foo": { "nested": "runner", "beans": [1,2,3] }, "test": [ { "query": "8" }, { "query": "9" } ] }

./json_tool file.json foo.beans.1
2

./json_tool file.json test.0.query
"8"
```

Notes
-----

Key names can be any alphanumeric string, ex: "1gfsa", "1", "test", "99ab12". This means that they will never contain the character '.'.


Requirements
------------

If you copy code from online source, please reference the source in your code's comments.


Levels
-----

There are 3 different levels of difficulty with 1 being the easiest.

Level 1:
You can use standard JSON parsing libraries.

Level 2:
You cannot use a library for JSON parsing.

Level 3:
This tool must be able to parse JSON files multiple orders of magnitude larger than the available RAM without paging to disk.