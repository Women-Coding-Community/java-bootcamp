# Commit and Push Changes to the Isabel Branch

## 1. Make Changes to Your Code
Open your code editor and modify the necessary files in your project.

---

## 2. Stage Your Changes
After making changes, stage them for commit using the command:

```bash
git add .
git add <file\_name>
```
---
## 3. Commit Your Changes
```bash
git commit -m "Your descriptive commit message here"

```
---


## 4. Push Changes to Isabel Branch
```bash
git push origin Isabel
```
---

Note: This push will create a pull request on the "main" branch in the Women-Coding-Community/java-bootcamp.

# Merge Changes from the Isabel Branch to master branch

## 1. Switch to the master Branch
```bash
git checkout master
```
---
## 2. Update Main Branch (Optional)
To ensure that your main branch is up to date, run:

```bash
git pull origin master
```
---
## 3. Merge Changes from Isabel Branch
```bash
git merge Isabel
```
---
## 4. Push Merged Changes to Main Branch
```bash
git push origin master
```
---
After pushing, your changes from the Isabel branch will be merged into the remote master branch.