Steps :
*Create a new Module*
1. Go to file -> New -> New Module -> Android Library -> Give Module Name (like:news)
2. Follows the same steps for search module

*Create sub module*
1. Right click on news -> new -> new Module -> Android Library -> give module Name like (:news:mylibrary)
2. Follows the same step for news_domain & news_presentation

*For Build.Gradle*
1. Shift the project module
2. Right click(RC) on NewsApp main module -> Directory -> (directory name is reserved here) so name it as buildSrc 
3. RC on buildSrc create a file with specific name *build.gradle.kts*.
4. In this to enable kotlin dsl we have to write 2 line of code as shown below
5. RC on buildSrc crate new directory **src/main/java**
6. Crete new file Dependencies





