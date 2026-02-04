# How to Fork This Repository

Forking creates your own copy of this repository where you can work on your project independently.

## 📋 What is Forking?

Forking is like making your own personal copy of someone else's project. You can make changes to your copy without affecting the original repository.

## 🔧 Step-by-Step Guide

### 1. Fork the Repository on GitHub

1. **Navigate to the repository** page on GitHub
2. **Click the "Fork" button** in the top-right corner of the page

   ![Fork Button](https://docs.github.com/assets/cb-23088/images/help/repository/fork_button.png)

3. **Select your account** as the destination for the fork
4. **Wait** for GitHub to create your fork (this takes a few seconds)

### 2. Clone Your Fork to Your Local Machine

Once you've forked the repository, you need to download it to your computer:

```bash
# Replace <your-username> with your GitHub username
git clone https://github.com/<your-username>/java-bootcamp.git

# Navigate into the directory
cd java-bootcamp
```

### 3. Configure Git (First Time Only)

If you haven't used Git before, configure your identity:

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### 4. Add the Original Repository as "Upstream"

This allows you to pull in updates from the main repository:

```bash
# Add the original repo as a remote called "upstream"
git remote add upstream https://github.com/WCC-ORG/java-bootcamp.git

# Verify the remotes
git remote -v
```

You should see:
```
origin    https://github.com/<your-username>/java-bootcamp.git (fetch)
origin    https://github.com/<your-username>/java-bootcamp.git (push)
upstream  https://github.com/WCC-ORG/java-bootcamp.git (fetch)
upstream  https://github.com/WCC-ORG/java-bootcamp.git (push)
```

## 🔄 Keeping Your Fork Updated

To get the latest changes from the original repository:

```bash
# Fetch the latest changes from upstream
git fetch upstream

# Switch to your main branch
git checkout main

# Merge upstream changes
git merge upstream/main

# Push updates to your fork
git push origin main
```

## 🌿 Working on Your Project

### Create Your Project Folder

```bash
# Create your participant directory
mkdir -p participants/<your-name>/project

# Navigate to your project folder
cd participants/<your-name>/project
```

### Create a New Branch for Your Work (Recommended)

```bash
# Create and switch to a new branch
git checkout -b my-project-development

# Start coding!
```

## 💾 Saving Your Changes

### Regular Commits

Save your progress regularly with commits:

```bash
# Check what files have changed
git status

# Add files to staging
git add .

# Or add specific files
git add participants/<your-name>/project/src/Main.java

# Commit with a descriptive message
git commit -m "Add initial project structure"

# Push to your fork
git push origin my-project-development
```

## 📤 Submitting Your Project

When you're ready to submit, follow the [How to Submit Guide](./HOW_TO_SUBMIT.md).

## ❓ Common Questions

### Q: What's the difference between fork and clone?
**A:** Forking creates a copy on GitHub (online), while cloning downloads a repository to your computer.

### Q: Can I fork multiple times?
**A:** You can only have one fork of a repository per GitHub account, but you can delete and re-fork if needed.

### Q: Will my changes affect the original repository?
**A:** No! Your fork is completely independent. Changes only go to the original if you submit a Pull Request and it's accepted.

### Q: I made a mistake. Can I start over?
**A:** Yes! You can delete your fork and fork again, or create a new branch to start fresh.

## 🆘 Troubleshooting

### "Permission denied" when pushing
Make sure you're pushing to your fork, not the original repository:
```bash
git remote -v  # Check your remotes
```

### Merge conflicts when updating from upstream
If you have conflicts:
```bash
git fetch upstream
git checkout main
git merge upstream/main
# Resolve conflicts in your editor
git add .
git commit -m "Resolve merge conflicts"
git push origin main
```

## 📚 Additional Resources

- [GitHub Docs: Fork a repo](https://docs.github.com/en/get-started/quickstart/fork-a-repo)
- [GitHub Docs: Syncing a fork](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/syncing-a-fork)
- [Git Basics Tutorial](https://git-scm.com/book/en/v2/Getting-Started-Git-Basics)

---

**Next Step:** Once you've forked and cloned the repository, check out the [Project Ideas](./PROJECT_IDEAS.md) to choose what to build!