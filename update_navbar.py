import sys

def update_file(file_path, old_text, new_text):
    with open(file_path, 'r') as f:
        content = f.read()
    if old_text in content:
        new_content = content.replace(old_text, new_text)
        with open(file_path, 'w') as f:
            f.write(new_content)
        print(f"Updated {file_path}")
    else:
        print(f"Could not find old_text in {file_path}")

# Dashboard update
dashboard_path = 'src/main/resources/templates/traveler/dashboard/dashboard.html'
old_dashboard = """        <!-- LOGO -->
        <div class="logo">
            <span class="logo-icon">◉</span>
            TRIPINAJA
        </div>

        <!-- PROFILE -->
        <div class="profile-menu">

            <div class="profile-avatar">
                A
            </div>

            <span class="profile-name">
                Akbar
            </span>

        </div>"""

new_dashboard = """        <!-- LOGO -->
        <div class="logo">
            <span class="logo-icon">◉</span>
            TRIPINAJA
        </div>

        <!-- NAV CENTER (MENU) -->
        <div class="nav-center">
            <a href="/dashboard">Dashboard</a>
            <a href="/itinerary/list">My Trips</a>
        </div>

        <!-- PROFILE -->
        <a href="/profile" class="profile-menu">

            <div class="profile-avatar">
                A
            </div>

            <span class="profile-name">
                Akbar
            </span>

        </a>"""

update_file(dashboard_path, old_dashboard, new_dashboard)

# Profile update
profile_path = 'src/main/resources/templates/traveler/profile/profile.html'
old_profile = """    <!--  NAVBAR  -->
    <nav class="navbar dashboard-navbar">
        <div class="container">
            <!-- LOGO -->
            <div class="logo">
                <span class="logo-icon">◉</span>
                TRIPINAJA
            </div>

            <!-- NAV CENTER (MENU) -->
            <div class="nav-center">
                <a href="/">Home</a>
                <a href="/dashboard">Dashboard</a>
                <a href="/explore">Explore</a>
                <a href="/itinerary/list">My Trips</a>
                <a href="/profile" class="active">Profile</a>
            </div>

            <!-- PROFILE  -->
            <div class="profile-menu">
                <div class="profile-avatar">A</div>
                <span class="profile-name">Akbar</span>
            </div>
        </div>
    </nav>"""

new_profile = """    <!--  NAVBAR  -->
    <nav class="navbar dashboard-navbar">
        <div class="container">
            <!-- LOGO -->
            <div class="logo">
                <span class="logo-icon">◉</span>
                TRIPINAJA
            </div>

            <!-- NAV CENTER (MENU) -->
            <div class="nav-center">
                <a href="/dashboard">Dashboard</a>
                <a href="/itinerary/list">My Trips</a>
            </div>

            <!-- PROFILE  -->
            <a href="/profile" class="profile-menu">
                <div class="profile-avatar">A</div>
                <span class="profile-name">Akbar</span>
            </a>
        </div>
    </nav>"""

update_file(profile_path, old_profile, new_profile)
